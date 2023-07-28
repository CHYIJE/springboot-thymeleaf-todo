package com.example.my.domain.todo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.dto.LoginUserDTO;
import com.example.my.common.dto.ResponseDTO;
import com.example.my.domain.todo.dto.ReqTodoTableInsertDTO;
import com.example.my.domain.todo.dto.ReqTodoTableUpdateDoneYnDTO;
import com.example.my.domain.todo.dto.ResTodoTableDTO;
import com.example.my.model.todo.entity.TodoEntity;
import com.example.my.model.todo.repository.TodoRepository;
import com.example.my.model.user.entity.UserEntity;
import com.example.my.model.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV1 {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> getTodoTableData(LoginUserDTO loginUserDTO) {
        // TODO : 리파지토리에서 유저 기본키로 삭제되지 않은 할 일 목록 찾기
        String userId = loginUserDTO.getUser().getId();
        List<TodoEntity> todoList = todoRepository.findByUserEntity_IdAndDeleteDateIsNull(userId);
        // TODO : 응답 데이터로 리턴하기 (할 일 목록 조회에 성공하였습니다.)
        ResTodoTableDTO response = ResTodoTableDTO.of(todoList);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .code(0)
                        .message("할 일 목록 조회에 성공하였습니다.")
                        .data(response)
                        .build()
        );
    }
    @Transactional
    public ResponseEntity<?> insertTodoTableData(ReqTodoTableInsertDTO dto, LoginUserDTO loginUserDTO) {
        // TODO : 할 일을 입력했는지 확인
        String content = dto.getTodo().getContent();
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ResponseDTO.builder()
                            .code(1)
                            .message("할 일을 입력해주세요.")
                            .build());
        }
        // TODO : 리파지토리에서 유저 기본키로 삭제되지 않은 유저 찾기
        Long userId = loginUserDTO.getUser().getIdx();
        UserEntity user = userRepository.findByIdxAndDeleteDateIsNull(userId)  //아 뭔데 이건 짅ㄴ리ㅏㅁㄴ오;ㅏㅣ홎ㅎ
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        // TODO : 할 일 엔티티 생성
        TodoEntity todo = TodoEntity.builder()
                .content(content)
                .userEntity(user)
                .doneYn('N') // default 값으로 설정
                .createDate(LocalDateTime.now()) // 생성 시간 설정
                .build();

        // TODO : 할 일 엔티티 저장
        todoRepository.save(todo);
        // TODO : 응답 데이터로 리턴하기 (할 일 추가에 성공하였습니다.)
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .code(0)
                        .message("할 일 추가에 성공하였습니다.")
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<?> updateTodoTableData(Long todoIdx, ReqTodoTableUpdateDoneYnDTO dto, LoginUserDTO loginUserDTO) {
        // 리파지토리에서 할 일 기본키로 삭제되지 않은 할 일 찾기
        TodoEntity todo = todoRepository.findByIdxAndDeleteDateIsNull(todoIdx)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 할 일입니다."));

        // 할 일 작성자와 로그인 유저가 다르면 (권한이 없습니다.) 리턴
        Long userId = loginUserDTO.getUser().getIdx();
        if (!todo.getUserEntity().getIdx().equals(userId)) {
            return ResponseEntity.badRequest()
                    .body(ResponseDTO.builder()
                            .code(1)
                            .message("권한이 없습니다.")
                            .build());
        }

        // 할 일 doneYn 업데이트
        todo.setDoneYn(dto.getTodo().getDoneYn());
        // 할 일 엔티티 저장
        todoRepository.save(todo);

        // 응답 데이터로 리턴하기 (할 일 수정에 성공하였습니다.)
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .code(0)
                        .message("할 일 수정에 성공하였습니다.")
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<?> deleteTodoTableData(Long todoIdx, LoginUserDTO loginUserDTO) {
        // 리파지토리에서 할 일 기본키로 삭제되지 않은 할 일 찾기
        TodoEntity todo = todoRepository.findByIdxAndDeleteDateIsNull(todoIdx)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 할 일입니다."));

        // 할 일 작성자와 로그인 유저가 다르면 (권한이 없습니다.) 리턴
        Long userId = loginUserDTO.getUser().getIdx();
        if (!todo.getUserEntity().getIdx().equals(userId)) {
            return ResponseEntity.badRequest()
                    .body(ResponseDTO.builder()
                            .code(1)
                            .message("권한이 없습니다.")
                            .build());
        }

        // 할 일 deleteDate 업데이트
        todo.setDeleteDate(LocalDateTime.now());
        todoRepository.save(todo);

        // 응답 데이터로 리턴하기 (할 일 삭제에 성공하였습니다.)
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .code(0)
                        .message("할 일 삭제에 성공하였습니다.")
                        .build()
        );
    }
}