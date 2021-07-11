package com.siliconvalve.linktrack.controllers;

import com.google.common.hash.Hashing;
import com.siliconvalve.linktrack.data.LinkClickRepository;
import com.siliconvalve.linktrack.data.ShortLinkRepository;
import com.siliconvalve.linktrack.models.LinkClick;
import com.siliconvalve.linktrack.models.ShortLink;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping(value = "/sl")
public class ShortLinkController {

  @Autowired
	private ShortLinkRepository linkRepo;

  @Autowired
	private LinkClickRepository trackRepo;

  /**
   * Returns the original URL.
   */
  @GetMapping(value = "/{id}")
  @ResponseBody
  public ResponseEntity getUrl(@PathVariable String id) {

    // get from redis
    ShortLink link = linkRepo.findById(id).orElse(null);

    if (link == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(link);
  }

    /**
   * Redirect request to URL and track click
   */
  @GetMapping(value = "/tk/{id}")
  @ResponseBody
  public ResponseEntity trackUrlClick(@PathVariable String id, @RequestHeader(value="User-Agent") String userAgent, @RequestHeader(value="Referer",defaultValue="-") String referer) {

    // get from redis
    ShortLink link = linkRepo.findById(id).orElse(null);

    if (link == null) {
      return ResponseEntity.notFound().build();
    }

    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    trackRepo.insert(new LinkClick(link.getId(), userAgent, request.getRemoteAddr(), LocalDateTime.now()));

    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link.getUrl())).build();
  }

  /**
   * Create a short URL entry.
   */
  @PostMapping(value="/create")
  @ResponseBody
  public ResponseEntity postUrl(@RequestBody @NonNull ShortLink link) {

    // if invalid url, return error
    if (!isUrlValid(link.getUrl())) {
      return ResponseEntity.badRequest().body("Invalid URL");
    }

    // See if the shortlink already exists
    // basic matching on URL only
    ShortLink lookup = new ShortLink();
    lookup.setUrl(link.getUrl());
    Example<ShortLink> linkToFind = Example.of(lookup);
    ShortLink returnedLink = linkRepo.findOne(linkToFind).orElse(null);

    if (returnedLink != null) {
      return ResponseEntity.ok(returnedLink);
    }

    // ShortLink doesn't appear to exist, so let's create it.
    String id = Hashing.murmur3_32().hashString(link.getUrl(), Charset.defaultCharset()).toString();
    link.setId(id);
    link.setCreated(LocalDateTime.now());
    link.setVisitors(0);

    //store in mongo
    linkRepo.insert(link);

    return ResponseEntity.created(URI.create("/sl/" + id)).build();
  }

  private static boolean isUrlValid(String url) {
    try {
       URL obj = new URL(url);
       obj.toURI();
       return true;
    } catch (MalformedURLException e) {
       return false;
    } catch (URISyntaxException e) {
       return false;
    }
 }

}