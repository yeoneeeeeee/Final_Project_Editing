package com.kh.dots.member.controller;

import com.kh.dots.common.model.vo.Images;
import com.kh.dots.feed.model.vo.Feed;
import com.kh.dots.feed.model.vo.MyFeed;
import com.kh.dots.feed.model.vo.req.FeedReq;
import com.kh.dots.feed.model.vo.res.ApiRes;
import com.kh.dots.feed.model.vo.res.FeedRes;
import com.kh.dots.feed.service.FeedService;
import com.kh.dots.member.model.service.MemberService;
import com.kh.dots.member.model.vo.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberApiController extends BaseController{

    private final MemberService mService;
    private final ServletContext application;

    private final FeedService fService;

    public static final String WEB_PATH = "resources/images/";

    @GetMapping("/sendSMS/{aaa}")
    public ResponseEntity<ApiModel> sendSMS(@PathVariable("aaa") String userPhone) {

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        log.debug("수신자 번호 : {}" , userPhone);
        log.debug("인증번호 : {}" , numStr);
//        mService.certifiedPhoneNumber(userPhone,numStr);

        ApiModel aaa = new ApiModel();
        aaa.setUserPhone(userPhone);
        aaa.setNumStr(numStr);

        return new ResponseEntity<>(aaa, HttpStatus.OK);
    }

    @GetMapping("/MyFeedEnroll")
    public ResponseEntity<MyFeed> MyFeedEnroll() {
        Member loginUser = super.getLoginUser();
        Images profileImg = mService.selectListImages(loginUser.getUserNo());
        log.debug("loginUser : {}" , loginUser);
        MyFeed f = new MyFeed();
        f.setLoginUser(loginUser);
        f.setProfileImg(profileImg);

        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    // new Feed 작성
    @PostMapping("/MyFeedModify")
    public ResponseEntity<ApiRes> feedEnroll(FeedReq req) {
        String code = "S";
        req.setMember(super.getLoginUser());

        FeedRes result = fService.insertFeedEnroll(req);

        if(result.getResult()  < 2) {
            code = "E";
        }

        return super.setResponse(result.getFeed(), code);
    }

    @GetMapping("/feedEnroll")
    public ResponseEntity<Feed> MyFeedEdit(int fno) {
        Member loginUser = super.getLoginUser();
        Images profileImg = mService.selectListImages(loginUser.getUserNo());

        Feed feed = fService.selectFeedData(fno);
        List<Images> feedImgs2 = fService.selectFeedImgList(fno);

        Feed f = new Feed();
        f.setFeedWriter(loginUser.getUserNo());
        f.setFeedImgs2(feedImgs2);

        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    // Feed 수정
    @PostMapping("/feedEdit")
    public ResponseEntity<Feed> feedEdit(HttpSession session, Feed feed, String hashTag,
                           MultipartFile feedImg1,
                           MultipartFile feedImg2,
                           MultipartFile feedImg3,
                           MultipartFile feedImg4,
                           MultipartFile feedImg5,
                           int fno,
                           String deleteList,
                           Model model
    ) {
        Member loginUser = super.getLoginUser();
        feed.setFeedHashtag(hashTag);
        feed.setFeedWriter(loginUser.getUserNo());
        feed.setFeedNo(fno);

        String webPath = "resources/images";
        String severFolderPath = application.getRealPath(webPath+"/");
        List<MultipartFile> upfiles = new ArrayList();
        upfiles.add(feedImg1);
        upfiles.add(feedImg2);
        upfiles.add(feedImg3);
        upfiles.add(feedImg4);
        upfiles.add(feedImg5);
        int result = fService.updateFeed(feed, upfiles, severFolderPath, webPath, deleteList);

        Feed f = new Feed();
        f.setFeedHashtag(hashTag);
        f.setFeedWriter(loginUser.getUserNo());
        f.setFeedNo(fno);
        f.setUpfiles(upfiles);

        if(result > 0) {
            session.setAttribute("alertMsg","게시물 수정 성공!");
            return new ResponseEntity<>(f, HttpStatus.OK);
        }else {
            session.setAttribute("alertMsg","게시물 수정 실패!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
