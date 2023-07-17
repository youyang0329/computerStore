package com.example.computerstore.controller;

import com.example.computerstore.pojo.District;
import com.example.computerstore.service.IDistrictService;
import com.example.computerstore.util.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("districts")
@RestController
public class DistrictController extends BaseController {
    @Autowired
    private IDistrictService districtService;

    //后面什么也不加，或者是districts下的全部都要
    @GetMapping({"", "/"})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }

}
