package com.ktds.board.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.board.service.BoardService;
import com.ktds.board.vo.BoardVO;
import com.ktds.common.web.DownloadUtil;
import com.ktds.member.vo.MemberVO;

@Controller
public class BoardController {

	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/write")
	public String viewBoardWrite() {
		return "board/write";
	}
	

	@PostMapping("/board/write")
	public String doBoardWriteAction(@ModelAttribute BoardVO boardVO, HttpSession session) {
		
		MultipartFile uploadFile = boardVO.getFile();
		
		if ( !uploadFile.isEmpty() ) {
			
			// 실제 파일 이름
			String originFileName = uploadFile.getOriginalFilename();
			// 파일 시스템에 저장될 파일의 이름(난수)
			String fileName = UUID.randomUUID().toString();
			
			File uploadDir = new File(this.uploadPath);
			// 폴더가 존재하지 않는다면 생성
			if ( !uploadDir.exists() ) {
				uploadDir.mkdirs();
			}
			
			// 파일이 업로드될 경로 지정
			File destFile = new File(this.uploadPath, fileName);
			
			try {
				// 업로드
				uploadFile.transferTo(destFile);
				// DB에 File 정보 저장하기 위한 정보 셋팅
				boardVO.setOriginFileName(originFileName);
				boardVO.setFileName(fileName);
			} catch (IllegalStateException | IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			
		}
		
		MemberVO loginMemberVO = (MemberVO) session.getAttribute("_USER_");
		String email = loginMemberVO.getEmail();
		boardVO.setEmail(email);
		
		return this.boardService.createOneBoard(boardVO, loginMemberVO) ? 
				"redirect:/board/list" : "redirect:/board/write";
	}
	
	@RequestMapping("/board/list")
	public ModelAndView viewBoardListPage() {
		
		//Integer.parseInt("ABC");
		List<BoardVO> boardVOList = this.boardService.readAllBoard();
		
		ModelAndView view = new ModelAndView("board/list");
		view.addObject("boardVOList", boardVOList);
		
		return view;
	}
	
	@RequestMapping("/board/detail/{id}")
	public ModelAndView viewBoardDetailPage(
			@PathVariable int id
			, @SessionAttribute("_USER_") MemberVO memberVO) {
		
		BoardVO boardVO = this.boardService.readOneBoard(id, memberVO);
		ModelAndView view = new ModelAndView("board/detail");
		view.addObject("boardVO", boardVO);
		
		return view;
	}
	
	@RequestMapping("/board/delete/{id}")
	public String doBoardDeleteAction(@PathVariable int id) {
		boolean isSuccess = this.boardService.deleteOneBoard(id);
		return "redirect:/board/list";
	}
	
	@RequestMapping("/board/download/{id}")
	public void fileDownload(
			@PathVariable int id
			, HttpServletRequest request
			, HttpServletResponse response
			, @SessionAttribute("_USER_") MemberVO memberVO) {
		
		BoardVO boardVO = this.boardService.readOneBoard(id);
		
		String originFileName = boardVO.getOriginFileName();
		String fileName = boardVO.getFileName();
		
		try {
			new DownloadUtil(this.uploadPath + File.separator + fileName)
			 .download(request, response, originFileName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
