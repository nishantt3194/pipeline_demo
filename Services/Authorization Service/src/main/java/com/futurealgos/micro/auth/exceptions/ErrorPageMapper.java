/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.exceptions;

import org.springframework.stereotype.Controller;

@Controller
public class ErrorPageMapper {

//    @RequestMapping("/error")
//    public ModelAndView handleError(HttpServletResponse response) throws IOException {
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error-page");
//        if(response.getStatus() == HttpStatus.NOT_FOUND.value()) {
//            modelAndView.getModelMap().addAttribute("code", 404);
//            modelAndView.getModelMap().addAttribute("tag", "Not Found");
//            modelAndView.getModelMap().addAttribute("message", "Could not find the endpoint you're looking for");
//        }
//
//        else if(response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//            modelAndView.getModelMap().addAttribute("code", 500);
//            modelAndView.getModelMap().addAttribute("tag", "Internal Server Error");
//            modelAndView.getModelMap().addAttribute("message", "Server ran into some problem");
//        }
//
//        else if(response.getStatus() == HttpStatus.FORBIDDEN.value()) {
//            modelAndView.getModelMap().addAttribute("code", 403);
//            modelAndView.getModelMap().addAttribute("tag", "Forbidden");
//            modelAndView.getModelMap().addAttribute("message", "You are not authorized to access this endpoint");
//        }
//
//        else {
//            modelAndView.getModelMap().addAttribute("code", 0);
//            modelAndView.getModelMap().addAttribute("tag", "Error Occurred");
//            modelAndView.getModelMap().addAttribute("message", "Server ran into some problem");
//        }
//
//        return modelAndView;
//    }


}
