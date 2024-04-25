package com.example.demo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.CreateMapDto;
import com.example.demo.DTO.ForumThreadCommentDto;
import com.example.demo.DTO.ForumThreadCommentsDto;
import com.example.demo.DTO.ForumThreadCreateDto;
import com.example.demo.DTO.ForumThreadDto;
import com.example.demo.DTO.ForumThreadLikeDto;
import com.example.demo.DTO.ForumThreadLikesDto;
import com.example.demo.DTO.MapsDto;
import com.example.demo.DTO.ResetPasswordDto;
import com.example.demo.DTO.ResetPasswordEmailDto;
import com.example.demo.DTO.UserBioDto;
import com.example.demo.DTO.UserLoginDto;
import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Exceptions.EmailExistsException;
import com.example.demo.Exceptions.EmailIsNotVerifiedException;
import com.example.demo.Exceptions.ExpiredOrInvalidTokenException;
import com.example.demo.Exceptions.InvalidEmailException;
import com.example.demo.Exceptions.InvalidIdException;
import com.example.demo.Exceptions.InvalidPasswordException;
import com.example.demo.Exceptions.InvalidResetTokenException;
import com.example.demo.Exceptions.ThreadAlreadyLikedByUserException;
import com.example.demo.Exceptions.ValuesInvalidException;
import com.example.demo.Exceptions.UsernameExistsException;
import com.example.demo.Exceptions.UsernameNotFoundException;
import com.example.demo.Services.ForumThreadCommentService;
import com.example.demo.Services.ForumThreadLikeService;
import com.example.demo.Services.ForumThreadService;
import com.example.demo.Services.MapService;
import com.example.demo.Services.SftpService;
import com.example.demo.Services.UserService;
import com.example.demo.Tables.Map;
import com.example.demo.Tables.UserAccount;

@RestController
public class RoutesController {

    private final UserService userService;
    private final ForumThreadService threadService;
    private final ForumThreadLikeService threadLikeService;
    private final ForumThreadCommentService threadCommentService;
    private final SftpService sftpService;
    private final MapService mapService;

    public RoutesController(UserService userService, ForumThreadService threadService,
            ForumThreadLikeService forumThreadLikeService, ForumThreadCommentService threadCommentService,SftpService sftpService, MapService mapService) {
        this.userService = userService;
        this.threadService = threadService;
        this.threadLikeService = forumThreadLikeService;
        this.threadCommentService = threadCommentService;
        this.sftpService=sftpService;
        this.mapService=mapService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserAccount>> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        userService.registerNewUser(registrationDto);
        ApiResponse<UserAccount> response = new ApiResponse<>("0", "Account created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserAccount>> loginUser(@RequestBody UserLoginDto loginDto) {
        UserAccount user = userService.loginUser(loginDto);
        ApiResponse<UserAccount> response = new ApiResponse<>("0", user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/updateBio")
    public ResponseEntity<ApiResponse<String>> updateBio(@RequestBody UserBioDto request) {
        userService.updateUserBio(request);
        ApiResponse<String> response = new ApiResponse<>("0", "Bio updated successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        UserAccount user;
        try {
            user = userService.getUserByVerificationToken(token);
        } catch (ExpiredOrInvalidTokenException e) {
            return ResponseEntity.ok("Invalid or expired verification token");
        }

        user.setEmailIsVerified(true);
        userService.saveUser(user);
        return ResponseEntity.ok("Email verified successfully");

    }

    @PostMapping("/thread/create")
    public ResponseEntity<ApiResponse<String>> createThread(@RequestBody ForumThreadCreateDto threadCreateDto) {
        threadService.createThread(threadCreateDto);
        ApiResponse<String> response = new ApiResponse<>("0", "Thread created successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/thread/all")
    public ResponseEntity<List<ForumThreadDto>> getAllThreads() {
        List<ForumThreadDto> threads = threadService.getAllThreads();
        return ResponseEntity.ok().body(threads);
    }

    @PostMapping("/user/sendPassword")
    public ResponseEntity<ApiResponse<UserAccount>> resetPassword(
            @RequestBody ResetPasswordEmailDto resetPasswordEmailDto) {
        UserAccount user = userService.getUserByEmail(resetPasswordEmailDto.getEmail());
        userService.setResetToken(resetPasswordEmailDto.getEmail());
        userService.sendResetEmail(resetPasswordEmailDto, user.getResetToken());
        ApiResponse<UserAccount> response = new ApiResponse<>("0", "Password reset email has been sent");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/resetPassword")
    public ResponseEntity<ApiResponse<UserAccount>> resetPasswordEmail(@RequestBody ResetPasswordDto resetPasswordDto) {
        userService.updateUserPassword(resetPasswordDto);
        ApiResponse<UserAccount> response = new ApiResponse<>("0", "Password has been reset");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/thread/like")
    public ResponseEntity<ApiResponse<String>> createThreadLike(@RequestBody ForumThreadLikeDto threadLikeDto) {
        threadLikeService.likeThread(threadLikeDto.getOwnerId(), threadLikeDto.getForumThreadId());
        threadLikeService.createForumThreadLike(threadLikeDto);
        ApiResponse<String> response = new ApiResponse<>("0", "Thread liked successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/thread/allLikes")
    public ResponseEntity<List<ForumThreadLikeDto>> getAllThreadLikes(@RequestBody ForumThreadLikesDto dto) {
        List<ForumThreadLikeDto> likes = threadLikeService.getLikesByThreadId(dto);
        return ResponseEntity.ok().body(likes);
    }

    @PostMapping("/thread/comment/create")
    public ResponseEntity<ApiResponse<String>> createThreadComment(@RequestBody ForumThreadCommentDto dto) {
        threadCommentService.createForumThreadComment(dto);
        ApiResponse<String> response = new ApiResponse<>("0", "Thread comment created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/thread/allComments")
    public ResponseEntity<List<ForumThreadCommentDto>> getAllThreadComments(@RequestBody ForumThreadCommentsDto dto) {
        List<ForumThreadCommentDto> likes = threadCommentService.getCommentsByThreadId(dto);
        return ResponseEntity.ok().body(likes);
    }

    @PostMapping("/user/changeProfilePicture")
    public ResponseEntity<ApiResponse<String>> handleProfilePictureUpload(@RequestParam("file") MultipartFile file,@RequestParam("id") Integer id) {
        try {
            String remoteDir = "/var/www/html/profilePictures";
            sftpService.uploadFileToSftp(file, remoteDir,"id"+".png");
            userService.updateUserProfilePicture(id);
            ApiResponse<String> response = new ApiResponse<>("0", "Profile picture has been changed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("2", "An issue has occured");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/maps/upload")
    public ResponseEntity<ApiResponse<String>> handleMapsUpload(CreateMapDto dto) {
        try {
            Map savedMap = mapService.uploadNewMap(dto);
            String remoteDir = "/var/www/html/maps";
            sftpService.uploadFileToSftp(dto.getFile(), remoteDir,savedMap.getId().toString()+".club");
            mapService.updateMapPath(savedMap.getId());
            ApiResponse<String> response = new ApiResponse<>("0", "Map has been uploaded");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("2", "An issue has occured");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/maps/all")
    public ResponseEntity<List<MapsDto>> getAllMaps() {
        List<MapsDto>maps = mapService.getAllMaps();
        return ResponseEntity.ok().body(maps);
    }

    // USERNAME IN USE
    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameExistsException(UsernameExistsException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // EMAIL IN USE
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleEmailExistsException(EmailExistsException e) {
        ApiResponse<String> response = new ApiResponse<>("2", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID USERNAME
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID PASSWORD
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidPasswordException(InvalidPasswordException e) {
        ApiResponse<String> response = new ApiResponse<>("2", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID TOKEN
    @ExceptionHandler(ExpiredOrInvalidTokenException.class)
    public ResponseEntity<ApiResponse<String>> handleExpiredOrInvalidTokenException(ExpiredOrInvalidTokenException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID ID
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidIdException(InvalidIdException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID EMAIL
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidEmailException(InvalidEmailException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID VALUES
    @ExceptionHandler(ValuesInvalidException.class)
    public ResponseEntity<ApiResponse<String>> handleThreadValuesInvalidException(ValuesInvalidException e) {
        ApiResponse<String> response = new ApiResponse<>("3", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // EMAIL NOT VERIFIED
    @ExceptionHandler(EmailIsNotVerifiedException.class)
    public ResponseEntity<ApiResponse<String>> handleEmailIsNotVerifiedException(EmailIsNotVerifiedException e) {
        ApiResponse<String> response = new ApiResponse<>("2", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // INVALID RESET TOKEN
    @ExceptionHandler(InvalidResetTokenException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidResetTokenException(InvalidResetTokenException e) {
        ApiResponse<String> response = new ApiResponse<>("1", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // THREAD ALREADY LIKED BY USER
    @ExceptionHandler(ThreadAlreadyLikedByUserException.class)
    public ResponseEntity<ApiResponse<String>> handleThreadAlreadyLikedByUserException(
            ThreadAlreadyLikedByUserException e) {
        ApiResponse<String> response = new ApiResponse<>("2", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
