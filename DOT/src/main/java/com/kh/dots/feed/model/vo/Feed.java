package com.kh.dots.feed.model.vo;

import java.sql.Date;
import java.util.List;


import com.kh.dots.common.model.vo.Images;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feed {
	
	private int feedNo; //	FEED_NO
	private int feedWriter; //	FEED_WRITER
	private String feedContent; // FEED_CONTENT
	private String likeStatus; //	LIKE_STATUS
	private String choiceStatus; //	CHOICE_STATUS
	private String likeCount; //	LIKE_COUNT
	private String feedHashtag; //	FEED_HASHTAG
	
	private String userNick;

	private List<MultipartFile> feedImgs;
	private List<Images> imgs;
	private List<Images> feedImgs2;
	private List<MultipartFile> upfiles;

}
