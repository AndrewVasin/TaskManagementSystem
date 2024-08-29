package com.example.taskmanager.persist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * User DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "UserCreateRequest", description = "User DTO")
public class UserCreateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public UserCreateRequest email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     * @return email
     */
    @NotEmpty
    @Size(min = 3, max = 255)
    @Schema(name = "email", example = "user@mail.com")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserCreateRequest password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     * @return password
     */
    @NotEmpty
    @Schema(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCreateRequest userCreateRequest = (UserCreateRequest) o;
        return Objects.equals(this.email, userCreateRequest.email) &&
                Objects.equals(this.password, userCreateRequest.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserCreateRequest {\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
