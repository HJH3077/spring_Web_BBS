package com.ict.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.vo.BVO;
import com.ict.vo.CVO;

@Repository("myDAOImpl")
public class MyDAOImpl implements MyDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int selectCount() throws Exception {
		return sqlSessionTemplate.selectOne("bbs_t.count");
	}

	@Override
	public List<BVO> selectBVOList(int begin, int end) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("begin", begin);
		map.put("end", end);
		return sqlSessionTemplate.selectList("bbs_t.list", map);
	}

	@Override
	public int insertBVO(BVO bvo) throws Exception {
		return sqlSessionTemplate.insert("bbs_t.insert", bvo);
	}

	// 이 두개는 동시 진행
	@Override
	public int updateBVOHit(String b_idx) throws Exception {
		return sqlSessionTemplate.update("bbs_t.hitup", b_idx);
	}
	@Override
	public BVO selectBVOOneList(String b_idx) throws Exception {
		return sqlSessionTemplate.selectOne("bbs_t.onelist", b_idx);
	}

	// 댓글 불러오기
	@Override
	public List<CVO> selectCVOList(String b_idx) throws Exception {
		return sqlSessionTemplate.selectList("bbs_t.clist", b_idx);
	}
	
	// 댓글 삽입
	@Override
	public int insertCVO(CVO cvo) throws Exception {
		return sqlSessionTemplate.insert("bbs_t.c_insert", cvo);
	}
	
	// 댓글 삭제
	@Override
	public int deleteCVO(String c_idx) throws Exception {
		return sqlSessionTemplate.delete("bbs_t.c_delete", c_idx);
	}
	
	// 원글의 댓글 모두 삭제
	@Override
	public int deleteCVOComm_All(String b_idx) throws Exception {
		return sqlSessionTemplate.delete("bbs_t.c_delete_all", b_idx);
	}

	// 원글 삭제
	@Override
	public int deleteBVO(String b_idx) throws Exception {
		return sqlSessionTemplate.delete("bbs_t.delete", b_idx);
	}

	// 비밀번호 체크
	@Override
	public int selectPwdChk(BVO bvo) throws Exception {
		return sqlSessionTemplate.selectOne("bbs_t.pwd_chk", bvo);
	}
	
	@Override
	public int updateBVO(BVO bvo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}


	
}
