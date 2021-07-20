package com.ict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.dao.MyDAO;
import com.ict.vo.BVO;
import com.ict.vo.CVO;

@Service("myServiceImpl")
public class MyServiceImpl implements MyService {
	@Autowired
	private MyDAO myDAO;

	@Override
	public int selectCount() throws Exception {
		return myDAO.selectCount();
	}

	@Override
	public List<BVO> selectBVOList(int begin, int end) throws Exception {
		return myDAO.selectBVOList(begin, end);
	}

	@Override
	public int insertBVO(BVO bvo) throws Exception {
		return myDAO.insertBVO(bvo);
	}

	@Override
	public BVO updateBVO_selectBVOOneList(String b_idx) throws Exception {
		int result = myDAO.updateBVOHit(b_idx);
		BVO bvo = myDAO.selectBVOOneList(b_idx);
		return bvo;
	}

	// 댓글 불러오기
	@Override
	public List<CVO> selectCVOList(String b_idx) throws Exception {
		return myDAO.selectCVOList(b_idx);
	}

	// 댓글 삽입
	@Override
	public int insertCVO(CVO cvo) throws Exception {
		return myDAO.insertCVO(cvo);
	}

	// 댓글 삭제
	@Override
	public int deleteCVO(String c_idx) throws Exception {
		return myDAO.deleteCVO(c_idx);
	}

	// 원글의 댓글 모두 삭제
	@Override
	public int deleteCVOComm_All(String b_idx) throws Exception {
		return myDAO.deleteCVOComm_All(b_idx);
	}

	// 원글 삭제
	@Override
	public int deleteBVO(String b_idx) throws Exception {
		return myDAO.deleteBVO(b_idx);
	}

	// 비밀번호 체크
	@Override
	public int selectPwdChk(BVO bvo) throws Exception {
		return myDAO.selectPwdChk(bvo);
	}

	// 업데이트 전 정보 가져오기
	@Override
	public BVO selectBVOOneList(String b_idx) throws Exception {
		return myDAO.selectBVOOneList(b_idx);
	}

	@Override
	public int updateBVO(BVO bvo) throws Exception {
		return myDAO.updateBVO(bvo);
	}

}
