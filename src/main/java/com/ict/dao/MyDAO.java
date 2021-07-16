package com.ict.dao;

import java.util.List;

import com.ict.vo.BVO;
import com.ict.vo.CVO;

public interface MyDAO {
	int selectCount() throws Exception;
	List<BVO> selectBVOList(int begin, int end) throws Exception;
	int insertBVO(BVO bvo) throws Exception;
	BVO selectBVOOneList(String b_idx) throws Exception;
	// 원글과 관련된 댓글 모두 삭제
	int deleteCVOComm_All(String b_idx) throws Exception;
	// 원글 삭제
	int deleteBVO(String b_idx) throws Exception;
	// 원글 수정
	int updateBVO(BVO bvo) throws Exception;
	// 조회수 업데이터
	int updateBVOHit(String b_idx) throws Exception;
	// 댓글 가져오기
	List<CVO> selectCVOList(String b_idx) throws Exception;
	// 댓글 쓰기
	int insertCVO(CVO cvo) throws Exception;
	// 댓글 삭제
	int deleteCVO(String c_idx) throws Exception;
	// 비밀번호 검사
	int selectPwdChk(BVO bvo) throws Exception;
}
