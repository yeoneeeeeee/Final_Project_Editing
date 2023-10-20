package com.kh.dots.feed.model.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kh.dots.feed.model.vo.Feed;
import com.kh.dots.member.model.vo.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FeedReq {
    Feed feed;
    String hashTag;
    MultipartFile feedImg1;
    MultipartFile feedImg2;
    MultipartFile feedImg3;
    MultipartFile feedImg4;
    MultipartFile feedImg5;

    Member member;
}
