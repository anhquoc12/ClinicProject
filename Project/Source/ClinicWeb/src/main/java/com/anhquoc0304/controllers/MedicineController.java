/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.controllers;

import com.anhquoc0304.pojo.Category;
import com.anhquoc0304.pojo.Medicine;
import com.anhquoc0304.pojo.UnitMedicine;
import com.anhquoc0304.service.CategoryService;
import com.anhquoc0304.service.MedicineService;
import com.anhquoc0304.service.UnitMedicineService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
public class MedicineController {

    @Autowired
    private MedicineService medicineService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UnitMedicineService unitService;

    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("units", this.unitService.getUnits());
    }

    @RequestMapping("/admin/medicineList")
    public String medicines(Model model, @RequestParam Map<String, String> params) {
        String name = params.get("name");
        String categoryName = params.get("cate");
        if (categoryName != null && !categoryName.isEmpty()) {
            int id = Integer.parseInt(categoryName);
            model.addAttribute("medicines", this.medicineService.getMedicineByCategoryName(id));
        } else {
            model.addAttribute("medicines", this.medicineService.getMedicineByName(name));
        }
        model.addAttribute("categories", this.categoryService.getCategories());
        return "medicineList";
    }

    @RequestMapping("/admin/medicine")
    public String formMedicine(Model model) {
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("categories", this.categoryService.getCategories());
        model.addAttribute("units", this.unitService.getUnits());
        return "medicine";
    }

    @RequestMapping(value = "/admin/medicine", method = RequestMethod.POST)
    public String addMedicine(Model model,
            @ModelAttribute(value = "medicine") @Valid Medicine medicine,
            BindingResult br,
            HttpServletRequest request) {
        model.addAttribute("msgErr", null);
        if (!br.hasErrors()) {
            if (this.medicineService.addOrUpdateMedicine(medicine)) {
                return "redirect:/admin/medicineList";
            }
        }
        model.addAttribute("msgErr", "Có lỗi xảy ra");
        return "medicine";
    }

    @RequestMapping(value = "/admin/medicine/{id}")
    public String updateMedicine(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("medicine", this.medicineService.getMedicineById(id));
        return "medicine";
    }

//    @RequestMapping("/admin/medicine/categories")
//    public String category() {
//        return "category";
//    }

    @RequestMapping("/admin/medicine/unit-medicine")
    public String unitMedicine(Model model) {
        model.addAttribute("unitMedicine", new UnitMedicine());
        model.addAttribute("units", this.unitService.getUnits());
        return "unitMedicine";
    }

    @RequestMapping(value = "/admin/medicine/unit-medicine", method = RequestMethod.POST)
    public String addUnitMedicine(Model model,
            @ModelAttribute(value = "unitMedicine") @Valid UnitMedicine unit,
            BindingResult br, HttpServletRequest servlet) {
        model.addAttribute("msgErr", null);
        if (!br.hasErrors()) {
            if (this.unitService.addUnit(unit)) {
                return "redirect:/admin/medicine/unit-medicine";
            }
        }
        model.addAttribute("msgErr", "Có lỗi xảy ra");
        return "unitMedicine";
    }

    @RequestMapping("/admin/medicine/category")
    public String category(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("ccategories", this.categoryService.getCategories());
        return "category";
    }

    @RequestMapping(value = "/admin/medicine/category", method = RequestMethod.POST)
    public String addCategory(Model model, @ModelAttribute(value = "category") @Valid Category c,
            BindingResult br, HttpServletRequest servlet) {
        model.addAttribute("msgErr", null);
        if (!br.hasErrors()) {
            if (this.categoryService.addCategory(c)) {
                return "redirect:/admin/medicine/category";
            }
        }
        model.addAttribute("msgErr", "Có lỗi xảy ra");
        return "category";
    }
}
