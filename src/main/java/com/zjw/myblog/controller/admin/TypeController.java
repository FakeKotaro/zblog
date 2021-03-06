package com.zjw.myblog.controller.admin;

import com.zjw.myblog.pojo.Type;
import com.zjw.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable,
                        Model model) {

        model.addAttribute("page" , typeService.listType(pageable));
        return "admin-types";
    }

    @GetMapping("/types/input")
    public String input() {
        return "admin-type-publish";
    }

    @PostMapping("/types")
    public String post(Type type , RedirectAttributes attributes) {
        Type type1 = typeService.saveType(type);
        if (type1 == null) {
            attributes.addFlashAttribute("message" , "添加失败！");
        } else {
            attributes.addFlashAttribute("message" , "添加成功!");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/types";
    }
}
