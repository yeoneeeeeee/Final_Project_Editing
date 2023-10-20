package com.kh.dots.member.controller;

import com.kh.dots.feed.model.vo.res.ApiRes;
import com.kh.dots.feed.model.vo.res.FeedRes;
import com.kh.dots.member.model.vo.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {
    public static final String LOGIN_USER =  "loginUser";
    protected Member getLoginUser() {
        return (Member) this.getRequest().getSession().getAttribute(LOGIN_USER);
    }

    protected HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    protected ResponseEntity<ApiRes> setResponse(ApiRes res) {
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    protected ResponseEntity<ApiRes> setResponse(Object obj, String code) {
        ApiRes res = new ApiRes();
        res.setData(obj);
        res.setCode(code);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
