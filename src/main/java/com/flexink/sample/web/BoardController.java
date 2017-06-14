package com.flexink.sample.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.flexink.common.code.FxBootType;
import com.flexink.common.file.service.CommonFileService;
import com.flexink.common.utils.EditorUtils;
import com.flexink.domain.board.Board;
import com.flexink.domain.board.BoardType;
import com.flexink.domain.board.Comment;
import com.flexink.domain.board.repository.BoardRepositorySupport;
import com.flexink.sample.service.BoardService;
import com.flexink.sample.service.CommentService;
import com.flexink.vo.ParamsVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardSampleService;
	
	@Autowired
	CommentService commentSampleService;
	
	@Autowired
	BoardRepositorySupport boardRepositorySupport;
	
	@Autowired
    private CommonFileService commonFileService;
	
	private String editorImgPathKey = "upload.editor";
	private String fileSavePathKey = "upload.board";
	
	/********************************************************************
	 * @메소드명	: view
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 게시판 리스트
	 ********************************************************************/
	@GetMapping("/{boardType}")
	public String view(@PathVariable String boardType, ParamsVo params, ModelMap model) {
		params.put("type", boardType);
		model.put("list", boardSampleService.getList(params));
		return "/sample/boardList";
	}
	
	
	/********************************************************************
	 * @메소드명	: writeAriticle
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 글 쓰기 및 수정 페이지
	 ********************************************************************/
	@GetMapping(value="/{boardType}/article")
	public String writeAriticle(@PathVariable String boardType, Board board, ModelMap model) {
		if(board.getId() != null) {
			model.put("article", boardRepositorySupport.getArticle(board));
		}
		return "/sample/boardDetail";
	}
	
	/********************************************************************
	 * @메소드명	: viewArticle
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 게시글 상세 (읽기 전용)
	 ********************************************************************/
	@GetMapping(value="/{boardType}/article/{id}")
	public String viewArticle(@PathVariable String boardType, @PathVariable long id, Board board, ParamsVo params, ModelMap model) {
		board.setId(id);
		board.setType(new BoardType(boardType));
		Board article = boardSampleService.viewArticle(board);
		model.put("article", article);
		model.put("comments", commentSampleService.getComments(article));
		
		return "/sample/viewArticle";
	}
	
	
	/********************************************************************
	 * @메소드명	: saveAriticle
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 글 등록 및 수정
	 ********************************************************************/
	@PostMapping("/{boardType}/article")
	public String saveAriticle(@PathVariable String boardType, MultipartHttpServletRequest request, ParamsVo params, Board board) throws IOException {
		board.setType(new BoardType(boardType));
		if(params.getString("secret", "").equalsIgnoreCase("true")) {
			board.setSecret(FxBootType.Secret.Y);
		} else {
			board.setSecret(FxBootType.Secret.N);
		}
		
        boardSampleService.saveArticle(board);
        
        // editor에 업로드된 이미지 tempDir -> 디렉토리 수정
        commonFileService.filePathUpdate(EditorUtils.getImgIds(board.getContent()), editorImgPathKey);
        
        // Axfile tempDir -> 디렉토리 수정
        if(params.getArray("fileIds") != null && params.getArray("fileIds").size() > 0) {
        	commonFileService.filePathUpdate(params.getArray("fileIds"), fileSavePathKey, board.getType().getType(), board.getId());
        }
        
        // 파일업로드 (MultiPart 형식)
        /*UploadParameters uploadParameters = new UploadParameters();
        uploadParameters.setSavePath("upload.testBoard");
        uploadParameters.setMultipartFiles(request.getFiles("file"));
        uploadParameters.setTargetType(board.getType());
        uploadParameters.setTargetId(String.valueOf(board.getId()));
        
        commonFileService.uploads(uploadParameters);*/
        
		
		return "redirect:/board/" + boardType + "/article/" + board.getId();
	}
	
	/********************************************************************
	 * @메소드명	: deleteAriticle
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 글 삭제
	 ********************************************************************/
	@DeleteMapping(value="/{boardType}/article/{id}")
	public String deleteAriticle(@PathVariable String boardType, @PathVariable long id, Board board) {
		boardSampleService.deleteArticle(id);
        return "redirect:/board/"+boardType;
	}
	
	
	/********************************************************************
	 * @메소드명	: saveComment
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 댓글 등록
	 ********************************************************************/
	@PostMapping(value="/article/comment")
	@ResponseBody
	public boolean saveComment(ParamsVo params, Comment comment) {
		commentSampleService.saveComment(params, comment);
		return true;
	}
	
	/********************************************************************
	 * @메소드명	: deleteComment
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 댓글 삭제
	 ********************************************************************/
	@DeleteMapping(value="/article/comment/{id}", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void deleteComment(@PathVariable long id) {
		commentSampleService.deleteComment(id);
	}
	
	/********************************************************************
	 * @메소드명	: updateComment
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 댓글 수정
	 ********************************************************************/
	@PutMapping(value="/article/comment/{id}", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateComment(@PathVariable long id, @RequestBody Comment comment) {
		comment.setId(id);
		commentSampleService.updateComment(comment);
	}
}
