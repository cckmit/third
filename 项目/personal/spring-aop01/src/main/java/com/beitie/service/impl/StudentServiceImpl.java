package com.beitie.service.impl;

import com.beitie.service.IStudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements IStudentService {
    public void study() {
        System.out.println("正在学习《静夜思》");
    }

    public void doPlay(String name, int amount) {
        System.out.println("学生们正在玩"+amount+"个"+name);
    }
}
