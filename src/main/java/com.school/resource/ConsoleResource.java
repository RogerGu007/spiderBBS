package com.school.resource;

import com.school.Service.NewsService;
import com.school.entity.ResponseBaseDTO;
import com.school.entity.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Component
@Path("/console")
public class ConsoleResource {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    NewsService newsService;

    @GET
    @Path("/manual/invalid")
    @Produces(MediaType.APPLICATION_JSON)
    public String manualInvalid(@QueryParam("id") String id,
                                @QueryParam("String") String linkUrl) {
        try {
            newsService.invalidNews(id, linkUrl);
        } catch (Exception e) {
            logger.error(e.getMessage() + String.format(" id=%s, linkUrl=%s 置news无效失败", id, linkUrl));
            return ResponseBaseDTO.render(Constant.RET_CODE_FAILURE, Constant.RET_MSG_FAILURE);
        }
        return ResponseBaseDTO.render(Constant.RET_CODE_SUCCESS, Constant.RET_MSG_SUCCESS);
    }

}
