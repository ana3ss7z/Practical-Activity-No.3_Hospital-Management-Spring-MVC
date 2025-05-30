package com.example.hospital_management.web;

import com.example.hospital_management.entites.Patient;
import com.example.hospital_management.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/user/index")
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
    @GetMapping("/admin/delete")
    public String delete(Long id,String Kw, int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?keyword="+Kw+"&page="+page;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @PostMapping("/admin/save")
    public String save(Model model , @Valid Patient patient, BindingResult bindingResult, String Kw, int page){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index?keyword="+Kw+"&page="+page;
    }
    @GetMapping("/admin/edit")
    public  String edit(Model model,Long id, String Kw, int page){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient not found");
//        if(bindingResult.hasErrors()) return "editPatients";
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",Kw);
        model.addAttribute("page",page);
        return "editPatient";

    }

}
