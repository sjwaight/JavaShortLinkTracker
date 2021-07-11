package com.siliconvalve.linktrack.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.siliconvalve.linktrack.models.ShortLink;

public interface ShortLinkRepository extends MongoRepository<ShortLink, String> {

}