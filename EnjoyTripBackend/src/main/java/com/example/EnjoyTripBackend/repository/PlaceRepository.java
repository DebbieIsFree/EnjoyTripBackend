package com.example.EnjoyTripBackend.repository;

import com.example.EnjoyTripBackend.domain.Place;
import com.example.EnjoyTripBackend.dto.PageRequestList;
import com.example.EnjoyTripBackend.dto.place.MyPlaceResponseDto;
import com.example.EnjoyTripBackend.dto.place.PlaceRequestDto;
import com.example.EnjoyTripBackend.dto.place.PlaceResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PlaceRepository {

    Long save(Place place);
    List<PlaceResponseDto> findAll(PageRequestList<?> requestList);
    Long findTotalCount();
    List<Optional<PlaceResponseDto>> findSearchWordPlaces(PageRequestList<?> requestList);
    Optional<PlaceResponseDto> findOne(Long id);
    Long updateBlog(@Param("id") Long id, @Param("placeRequestDto") PlaceRequestDto placeRequestDto);
    Long deleteBlog(@Param("id") Long id);
    List<Optional<MyPlaceResponseDto>> myPage(@Param("userEmail") String userEmail);
}