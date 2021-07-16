package com.ict.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.service.MyService;
import com.ict.service.Paging;
import com.ict.vo.BVO;
import com.ict.vo.CVO;

@Controller
public class MyController {
	@Autowired
	private MyService myService;
	@Autowired
	private Paging paging;

	// page는 여기서 처리해도되고 service package에서 proccess라는 자바파일을 만들어서 처리하고 넘겨줘도 상관없음
	@RequestMapping("list.do")
	public ModelAndView getListCommand(@ModelAttribute("cPage") String cPage) {
		try {
			ModelAndView mv = new ModelAndView("list");
			// 전체 게시물의 수
			int count = myService.selectCount();
			paging.setTotalRecord(count);

			// 전체 페이지의 수
			// 게시물수 < 한 페이지의 게시물 수
			if (paging.getTotalRecord() < paging.getNumPerPage()) {
				paging.setTotalPage(1);
			} else {
				paging.setTotalPage(paging.getTotalRecord() / paging.getNumPerPage());
				// 나머지가 존재하면 전체 페이지에서 1증가
				if (paging.getTotalRecord() % paging.getNumPerPage() != 0) {
					paging.setTotalPage(paging.getTotalPage() + 1);
				}
			}

			// 현재 페이지 구하기
			paging.setNowPage(Integer.parseInt(cPage));

			// 시작번호, 끝번호 (글의 시작번호와 끝번호)
			paging.setBegin((paging.getNowPage() - 1) * paging.getNumPerPage() + 1);
			paging.setEnd((paging.getBegin() - 1) + paging.getNumPerPage());

			// 시작블록, 끝블록
			paging.setBeginBlock(
					(int) ((paging.getNowPage() - 1) / paging.getPagePerBlock()) * paging.getPagePerBlock() + 1);
			paging.setEndBlock(paging.getBeginBlock() + paging.getPagePerBlock() - 1);
			// 주의사항 : EndBlock이 totalPage보다 큰 경우가 발생할 수 있다.
			// ( Ex) 5개 블록하려고 했는데 페이지가 5개 보다 적은 경우 )
			// 이 경우 EndBlock을 totalPage에 맞춰야 한다.
			if (paging.getEndBlock() > paging.getTotalPage()) {
				paging.setEndBlock(paging.getTotalPage());
			}

			// 시작번호와 끝번호를 이용해서 list를 구하자
			List<BVO> list = myService.selectBVOList(paging.getBegin(), paging.getEnd());
			mv.addObject("list", list);
			mv.addObject("pvo", paging);
			return mv;
		} catch (Exception e) {
			System.out.println(e);
			return new ModelAndView("error");
		}
	}

	@RequestMapping("write.do")
	public ModelAndView writeCommand(@ModelAttribute("cPage") String cPage) {
		return new ModelAndView("write");
	}

	@RequestMapping(value = "write_ok.do", method = RequestMethod.POST) // multipart이므로 이렇게 작성
	public ModelAndView writeOkCommand(BVO bvo, HttpServletRequest request, @ModelAttribute("cPage") String cPage) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/resources/upload");
			MultipartFile file = bvo.getF_name(); // write에서 넣은 파일
			if (file.isEmpty()) { // write에서 넣은 파일이 비었으면
				bvo.setFile_name(""); // DB의 파일도 비움
			} else {
				bvo.setFile_name(file.getOriginalFilename()); // 파일이 안 비었으니 DB의 파일을 write에서 넣은 파일 이름으로
				// 첨부파일을 무조건 넣어야 하면 여기에다가 byte부분을 씀
			}
			int result = myService.insertBVO(bvo);
			if (result > 0) {
				if (!bvo.getFile_name().isEmpty()) { // DB에 넣을 파일 이름이 있으면 upload
					byte[] in = file.getBytes();
					File out = new File(path, bvo.getFile_name());
					FileCopyUtils.copy(in, out);
				}

				return new ModelAndView("redirect:list.do?cPage=" + cPage); // 이런식으로 항상 cPage를 달고 다니게 함
			} else {
				return new ModelAndView("redirect:write.do?cPage=" + cPage);
			}
		} catch (Exception e) {
			System.out.println(e);
			return new ModelAndView("redirect:write.do?cPage=" + cPage);
		}
	}

	@RequestMapping("onelist.do")
	public ModelAndView onelistCommand(@RequestParam("b_idx") String b_idx, @ModelAttribute("cPage") String cPage) {
		try {
			ModelAndView mv = new ModelAndView();
			// 조회수 업데이트 및 상세보기
			BVO bvo = myService.updateBVO_selectBVOOneList(b_idx);

			mv.addObject("bvo", bvo);
			return mv;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/*
	 * @RequestMapping("download.do") public
	 */

	@RequestMapping(value = "clist.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<CVO> cList(@RequestParam("b_idx") String b_idx) {
		try {
			return myService.selectCVOList(b_idx);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@RequestMapping(value = "c_insert.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int c_insertCommand(CVO cvo){
		try {
			return myService.insertCVO(cvo);
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	@RequestMapping(value = "c_delete.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int c_deletCommand(@RequestParam("c_idx") String c_idx) {
		try {
			return myService.deleteCVO(c_idx);
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	@RequestMapping("delete.do")
	public ModelAndView deleteCommand(@ModelAttribute("cPage") String cPage, @ModelAttribute("b_idx") String b_idx) {
		return new ModelAndView("delete");
	}

	@RequestMapping(value = "pwd_chk.do", produces = "html/text; charset=utf-8")
	@ResponseBody
	public String pwd_chkCommand(@ModelAttribute("b_idx") String b_idx,
			@ModelAttribute("pwd")String pwd) {
		try {
			BVO bvo = new BVO();
			bvo.setB_idx(b_idx);
			bvo.setPwd(pwd);
			int result = myService.selectPwdChk(bvo);
			return String.valueOf(result);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@RequestMapping("delete_ok.do")
	public ModelAndView deleteOKCommand(@ModelAttribute("cPage")String cPage,
			@ModelAttribute("b_idx")String b_idx) {
		try {
			int result1 = myService.deleteCVOComm_All(b_idx); 
			int result2 = myService.deleteBVO(b_idx);
			return new ModelAndView("redirect:list.do?cPage="+cPage);
		} catch (Exception e) {
		}
		return null;
		
	}
	 
	
}
