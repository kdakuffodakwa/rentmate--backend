package rentmate.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        User user = userService.register(
                body.get("fullName"),
                body.get("phoneNumber"),
                body.get("email"),
                body.get("password"),
                body.get("role")
        );
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "fullName", user.getFullName(),
                "phoneNumber", user.getPhoneNumber(),
                "role", user.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String token = userService.login(
                body.get("phoneNumber"),
                body.get("password")
        );
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable java.util.UUID userId) {
        return userService.getUserById(userId)
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(Map.of(
                        "id", user.getId(),
                        "fullName", user.getFullName(),
                        "phoneNumber", user.getPhoneNumber(),
                        "email", user.getEmail() != null ? user.getEmail() : "",
                        "role", user.getRole(),
                        "isVerified", user.getIsVerified(),
                        "avgRating", user.getAvgRating()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable java.util.UUID userId, @RequestBody Map<String, String> body) {
        User user = userService.updateUser(userId, body.get("fullName"), body.get("email"));
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "fullName", user.getFullName(),
                "phoneNumber", user.getPhoneNumber(),
                "email", user.getEmail() != null ? user.getEmail() : "",
                "role", user.getRole()
        ));
    }
}