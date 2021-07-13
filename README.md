# Sample Java Spring Boot Link Tracker

This simple application is designed to create, view and track use of shortlinks.

You can create shortlinks for all your outbound links on your blog or website and then use this solution to track.

The backend is stored in a MongoDB database, with Links in one Collection and Link Track entries in another.

To debug locally you will need:

- A Java 11 JDK installed.
- Visual Studio Code with the excellent [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) installed.
- A local (or remote) MongoDB (v4) database running with a database provisioned.
- A REST client. Check out the awesome Thunderclient for Visual Studio Code.

Rename the application.properties.prod file to application.properties and then add the values you require for the config items.

The API consists of three endpoints:

- **Create Shortlink (POST) - /sl/create** - Basic Auth protected endpoint that allows you to create a new shortlink entry (if it already exists then the current record is returned). Post JSON in the format:

```json
{
    "url" : "https://blog.siliconvalve.com/1234"
}
```

- **Get Shortlik entry (GET)- /sl/{id}** - anonymouse endpoint that returns the full entry for a shortlink entry. You need to know the ID you are looking for (note: the ID isn't the target URL).
- **Track Shortlink click (GET) - /sl/tk/{id}** - use this in your source web page to track the click and redirect the browser to the target site.

You can test out these endpoints in your fully deployed solution by installing Thunderclient (https://www.thunderclient.io/) and importing the collection in VS Code that is [available in this repository](thunder-collection_Shortlinks%20Demo.json).

### References

The following repos or sites informed to the building of this sample:

- https://github.com/sanchigoyal/spring-url-shortener
- https://github.com/javatodev/spring-boot-mongo-db
- https://fullstackdeveloper.guru/2021/03/12/how-to-redirect-to-an-external-url-from-spring-boot-rest-controller/
- https://dzone.com/articles/optional-in-java
