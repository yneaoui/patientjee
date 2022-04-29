package com.emsi.patientsmvc.web;

import com.emsi.patientsmvc.entities.Patient;
import com.emsi.patientsmvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository PR;

    @GetMapping(path ={"/user/index"} )
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        Page<Patient> PagePatients = PR.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("ListPatients", PagePatients.getContent());
        model.addAttribute("pages", new int[PagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);


        return "patients";
    }
@GetMapping("/")
public String home(){
        return "home";
}

    @GetMapping("/admin/delete")
    public String delete(long id, String keyword, int page) {
        PR.deleteById(id);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;

    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }


@PostMapping(path="/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formPatients";
        PR.save(patient);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/editPatients")
    public String editPatients(Model model,Long id,String Keyword, int page) {
        Patient patient=PR.findById(id).orElse(null);
        if(patient==null)throw new RuntimeException("patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("Keyword",Keyword);
        return "editPatients";
    }
}


