package com.ict.service;

import java.util.List;

import com.ict.vo.BVO;
import com.ict.vo.CVO;

public interface MyService {
	int selectCount() throws Exception;
	List<BVO> selectBVOList(int begin, int end) throws Exception;
	int insertBVO(BVO bvo) throws Exception;
	
	/*
	 * // service에서는 이 두가지를 한꺼번에 할 수가 있다. BVO selectBVOOneList(String b_idx) throws
	 * Exception; int updateBVOHit(String b_idx) throws Exception;
	 */
	
	// 업데이트와 상세보기를 하나로
	BVO updateBVO_selectBVOOneList(String b_idx) throws Exception;
	
	// 원글과 관련된 댓글 모두 삭제
	int deleteCVOComm_All(String b_idx) throws Exception;
	// 원글 삭제
	int deleteBVO(String b_idx) throws Exception;
	// 원글 수정
	int updateBVO(BVO bvo) throws Exception;
	// 댓글 가져오기
	List<CVO> selectCVOList(String b_idx) throws Exception;
	// 댓글 쓰기
	int insertCVO(CVO cvo) throws Exception;
	// 댓글 삭제
	int deleteCVO(String c_idx) throws Exception;
	// 비밀번호 체크
	int selectPwdChk(BVO bvo) throws Exception;
	
	// 업데이트전에 정보 가져오기 
	BVO selectBVOOneList(String b_idx) throws Exception ;
}
