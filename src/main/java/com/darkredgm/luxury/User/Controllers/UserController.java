package com.darkredgm.luxury.User.Controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
public class UserController {

    @GetMapping("/")
    public void index()
    {

    }

    @GetMapping("/{id}")
    public void show(@PathVariable int id)
    {

    }

    @PostMapping("/")
    public void store()
    {

    }

    @PatchMapping("/{id}")
    public void update(@PathVariable int id)
    {

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id)
    {

    }
}
