package com.siliconvalve.linktrack.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.siliconvalve.linktrack.models.LinkClick;

public interface LinkClickRepository extends MongoRepository<LinkClick, String> {

}