package com.darkredgm.luxury.Auth;

import com.darkredgm.luxury.User.Models.User;

public record AuthResponse(User user, String token) {
}
