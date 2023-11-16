package ru.razin.finsup.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import ru.razin.finsup.models.*;

@RestController
public class FinSupController
{
    @Value("${strapi.url}")
    private String strapi_url;

    @PostMapping("/applications")
    public void getFinSupJson(@RequestBody FinSupAtrModel finSupAtrModel)
    {
        String finsup_json = "";
        try {
              ObjectMapper mapper = new ObjectMapper();
              finsup_json = mapper.writeValueAsString(finSupAtrModel);
            }
        catch (JsonProcessingException e)
           {
              e.printStackTrace();
           }
        System.out.println(finsup_json);

        StrapiAtrModel strapiAtrModel = new StrapiAtrModel();
        strapiAtrModel.setData(finSupAtrModel);
        String strapi_json = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            strapi_json = mapper.writeValueAsString(strapiAtrModel);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        System.out.println(strapi_json);

        System.out.println(strapi_url);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<StrapiAtrModel> data_post_req = new HttpEntity<>(strapiAtrModel);
        restTemplate.postForObject(strapi_url,data_post_req,String.class);



    }
}
