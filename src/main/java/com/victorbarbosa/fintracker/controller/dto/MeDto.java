package com.victorbarbosa.fintracker.controller.dto;

import java.util.List;

public record MeDto(String username, List<String> authorities) {
}
