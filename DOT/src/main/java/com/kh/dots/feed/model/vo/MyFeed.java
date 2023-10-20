package com.kh.dots.feed.model.vo;

import com.kh.dots.chatting.model.vo.ChatRoom;
import com.kh.dots.chatting.model.vo.ChatRoomJoin;
import com.kh.dots.common.model.vo.Images;
import com.kh.dots.common.model.vo.Search;
import com.kh.dots.member.model.vo.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyFeed {
    private Member loginUser;
    private Images profileImg;
    private List<Images> myImglist;
    private List<Member> follower;
    private List<Member> follow;
    private List<Choice> myChoice;
    private List<Search> MyHistory1;
    private List<Search> MyHistory;
    private List<ChatRoom> crList;
    private List<ChatRoomJoin> crImage;

    public MyFeed() {
    }
}
