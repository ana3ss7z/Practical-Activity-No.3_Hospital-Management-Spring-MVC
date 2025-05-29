package com.example.hospital_management.web;

import com.example.hospital_management.entites.Patient;
import com.example.hospital_management.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "5")int size,
                        @RequestParam(name = "keyword",defaultValue = "") String k) {
        Page<Patient> patientsPage = patientRepository.findByNomContains(k,PageRequest.of(page, size));
        model.addAttribute("patientsList", patientsPage.getContent());
        model.addAttribute("pages", new int[patientsPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",k);
        return "patients";

    }
    @GetMapping("/delete")
    public String delete(Long id,String Kw, int page){
        patientRepository.deleteById(id);
        return "redirect:/index?keyword="+Kw+"&page="+page;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }
}
