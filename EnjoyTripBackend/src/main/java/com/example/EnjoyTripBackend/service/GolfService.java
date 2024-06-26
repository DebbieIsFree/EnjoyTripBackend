package com.example.EnjoyTripBackend.service;

import com.example.EnjoyTripBackend.domain.Golf;
import com.example.EnjoyTripBackend.dto.NonPagingResponseResult;
import com.example.EnjoyTripBackend.dto.PageRequestList;
import com.example.EnjoyTripBackend.dto.ResponseResult;
import com.example.EnjoyTripBackend.dto.golf.GolfClubListDto;
import com.example.EnjoyTripBackend.dto.golf.GolfRequestDto;
import com.example.EnjoyTripBackend.dto.golf.GolfResponseDto;
import com.example.EnjoyTripBackend.exception.EnjoyTripException;
import com.example.EnjoyTripBackend.exception.ErrorCode;
import com.example.EnjoyTripBackend.repository.GolfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GolfService {

    private final GolfRepository golfRepository;

    @Transactional
    public Long save(String today, GolfClubListDto golfclubListDto) {
        Golf golf = Golf.builder()
                .place(golfclubListDto.getGolfclub_name().replace("&#40;P&#41;", "").replace("&#40;","").replace("&#41;",""))
                .location(golfclubListDto.getArea_name1() + golfclubListDto.getArea_name2())
                .date(today)
                .golfImageUrl(golfclubListDto.getThmbn_file_url())
                .greenFeeWeek(golfclubListDto.getGreen_fee_week())
                .greenFeeWeekend(golfclubListDto.getGreen_fee_weekend())
                .latitude(golfclubListDto.getLatitude())
                .longitude(golfclubListDto.getLongitude())
                .build();

        return golfRepository.save(golf);
    }

    public ResponseResult<List<GolfResponseDto>> golfList(Pageable pageable) {
        PageRequestList<?> requestList = PageRequestList.builder()
                .pageable(pageable)
                .build();

        long totalCount = golfRepository.findTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageable.getPageSize());

        return ResponseResult.of("골프 정보 게시글 목록입니다.", golfRepository.findAll(requestList),totalPages);
    }

    public NonPagingResponseResult<GolfResponseDto> findById(Long id) {
        return NonPagingResponseResult.of("골프 상세 정보 게시글 입니다.", golfRepository.findById(id).orElseThrow(() -> new EnjoyTripException(ErrorCode.CONTENT_NOT_FOUNT)));
    }

    public ResponseResult<List<GolfResponseDto>> golfSearchList(Pageable pageable, GolfRequestDto golfRequestDto) {
        PageRequestList<?> requestList = PageRequestList.builder()
                .pageable(pageable)
                .data(golfRequestDto)
                .build();
        List<GolfResponseDto> golfResponseDto = golfRepository.findAllBySearch(requestList);
        long totalCount = golfResponseDto.size();
        int totalPages = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        return ResponseResult.of("검색어 기반 골프 정보 게시글 목록입니다.", golfRepository.findAllBySearch(requestList),totalPages);
    }
}