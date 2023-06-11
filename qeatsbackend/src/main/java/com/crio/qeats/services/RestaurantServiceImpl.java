
/*
 *
 *  * Copyright (c) Crio.Do 2019. All rights reserved
 *
 */

package com.crio.qeats.services;

import com.crio.qeats.dto.Restaurant;
import com.crio.qeats.exchanges.GetRestaurantsRequest;
import com.crio.qeats.exchanges.GetRestaurantsResponse;
import com.crio.qeats.repositoryservices.RestaurantRepositoryService;
import com.crio.qeats.repositoryservices.RestaurantRepositoryServiceDummyImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

  private final Double peakHoursServingRadiusInKms = 3.0;
  private final Double normalHoursServingRadiusInKms = 5.0;
  @Autowired
  private RestaurantRepositoryService restaurantRepositoryService;

  
  @Override
  public GetRestaurantsResponse findAllRestaurantsCloseBy(GetRestaurantsRequest getRestaurantsRequest,
      LocalTime currentTime) {

    // For peak hours: 8AM - 10AM, 1PM-2PM, 7PM-9PM
    // * - service radius is 3KMs.
    // * - All other times, serving radius is 5KMs.
    // restaurantRepositoryService=new RestaurantRepositoryServiceDummyImpl();
    if (isPeak(currentTime)) {
      List<Restaurant> restaurants = restaurantRepositoryService.findAllRestaurantsCloseBy(
          getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), currentTime,
          peakHoursServingRadiusInKms);
      GetRestaurantsResponse getRestaurantsResponse = new GetRestaurantsResponse(restaurants);
      return getRestaurantsResponse;
    }
     else {
      List<Restaurant> restaurants = restaurantRepositoryService.findAllRestaurantsCloseBy(
          getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(), currentTime,
          normalHoursServingRadiusInKms);
      GetRestaurantsResponse getRestaurantsResponse = new GetRestaurantsResponse(restaurants);
      return getRestaurantsResponse;
    }
  }

  private boolean isPeak(LocalTime currentTime) {
    if (isBetween("08:00", "10:00", currentTime)){
      return true;
    }
    if (isBetween("13:00", "14:00", currentTime)){
      return true;
    }
    if (isBetween("19:00", "21:00", currentTime)){
      return true;
    }
    return false;
  }

  private boolean isBetween(String start, String end, LocalTime currentTime) {

    if (currentTime.isAfter(LocalTime.parse(start)) && currentTime.isBefore(LocalTime.parse(end))){
      return true;
    }
    if (currentTime.equals(LocalTime.parse(start)) || currentTime.equals(LocalTime.parse(end))){
      return true;
    }
    return false;
  }

  // @Bean
  // public RestaurantRepositoryService getRestaurantRepositoryService(){
  //   return new RestaurantRepositoryServiceDummyImpl();
  // }

}
