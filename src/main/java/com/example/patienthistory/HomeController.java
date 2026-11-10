
package com.example.patienthistory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final patientrepository patientRepository;
    private final HealthOverviewRepository healthOverviewRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    public HomeController(patientrepository patientRepository,
                           HealthOverviewRepository healthOverviewRepository,
                           MedicalHistoryRepository medicalHistoryRepository,
                           UserAccountRepository userAccountRepository,
                           PasswordEncoder passwordEncoder
    ) {
        this.patientRepository = patientRepository;
        this.healthOverviewRepository = healthOverviewRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.userAccountRepository = userAccountRepository;
this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistration(
            @RequestParam String fullName,
            @RequestParam int age,
            @RequestParam String gender,
            @RequestParam String contactNumber,
            Model model) {

        patient patient = new patient();
        patient.setFullName(fullName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setContactNumber(contactNumber);

        patient savedPatient = patientRepository.save(patient);

        model.addAttribute("patient", savedPatient);

        return "success";
    }
 @GetMapping("/patients")
public String showPatients(Model model) {
    model.addAttribute("patients", patientRepository.findAll());
    return "patients";
} 

@GetMapping("/health-overview")
public String showHealthOverviewForm() {
    return "health-overview";
}

@PostMapping("/health-overview")
public String saveHealthOverview(
        @RequestParam Long patientId,
        @RequestParam(required = false) String bloodGroup,
        @RequestParam(required = false) String allergies,
        @RequestParam(required = false) String leftEyePower,
        @RequestParam(required = false) String rightEyePower,
        @RequestParam(required = false) Double height,
        @RequestParam(required = false) Double weight,
        @RequestParam(required = false) String existingConditions,
        @RequestParam(required = false) String currentMedicines,
        Model model) {

    HealthOverview overview = new HealthOverview();
    overview.setPatientId(patientId);
    overview.setBloodGroup(bloodGroup);
    overview.setAllergies(allergies);
    overview.setLeftEyePower(leftEyePower);
    overview.setRightEyePower(rightEyePower);
    overview.setHeight(height);
    overview.setWeight(weight);
    overview.setExistingConditions(existingConditions);
    overview.setCurrentMedicines(currentMedicines);

    healthOverviewRepository.save(overview);

    model.addAttribute("patientId", patientId);
    return "health-overview-success";
}

@GetMapping("/view-history/{id}")
public String viewHistory(
        @org.springframework.web.bind.annotation.PathVariable Long id,
        Model model) {

    patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

    model.addAttribute("patient", patient);

    HealthOverview overview = healthOverviewRepository
            .findByPatientId(id)
            .orElse(null);

    model.addAttribute("overview", overview);

    model.addAttribute("medicalHistories",
            medicalHistoryRepository.findByPatientId(id));

    return "view-history";
}
@GetMapping("/add-medical-history")
public String showMedicalHistoryForm(Model model) {
    model.addAttribute("patients", patientRepository.findAll());
    return "add-medical-history";
}

@PostMapping("/add-medical-history")
public String saveMedicalHistory(
        @RequestParam Long patientId,
        @RequestParam String disease,
        @RequestParam(required = false) String symptoms,
        @RequestParam(required = false) String doctorNotes,
        @RequestParam(required = false) String treatment) {

    MedicalHistory history = new MedicalHistory();
    history.setPatientId(patientId);
    history.setDisease(disease);
    history.setSymptoms(symptoms);
    history.setDoctorNotes(doctorNotes);
    history.setTreatment(treatment);

    medicalHistoryRepository.save(history);

    return "redirect:/view-history/" + patientId;
}
@GetMapping("/edit-medical-history/{id}")
public String editMedicalHistory(
        @org.springframework.web.bind.annotation.PathVariable Long id,
        Model model) {

    MedicalHistory history = medicalHistoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medical history not found"));

    model.addAttribute("history", history);
    model.addAttribute("patients", patientRepository.findAll());

    return "edit-medical-history";
}
@PostMapping("/edit-medical-history/{id}")
public String updateMedicalHistory(
        @org.springframework.web.bind.annotation.PathVariable Long id,
        @RequestParam String disease,
        @RequestParam(required = false) String symptoms,
        @RequestParam(required = false) String doctorNotes,
        @RequestParam(required = false) String treatment) {

    MedicalHistory history = medicalHistoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medical history not found"));

    history.setDisease(disease);
    history.setSymptoms(symptoms);
    history.setDoctorNotes(doctorNotes);
    history.setTreatment(treatment);

    medicalHistoryRepository.save(history);

    return "redirect:/view-history/" + history.getPatientId();
}
@GetMapping("/search-patient")
public String searchPatient(
        @RequestParam(required = false) String keyword,
        Model model) {

    if (keyword != null && !keyword.trim().isEmpty()) {
        try {
            Long id = Long.parseLong(keyword.trim());
            model.addAttribute("patients",
                    patientRepository.findById(id)
                            .map(java.util.List::of)
                            .orElse(java.util.List.of()));
        } catch (NumberFormatException e) {
            model.addAttribute("patients",
                    patientRepository.findByFullNameContainingIgnoreCase(
                            keyword.trim()));
        }
    } else {
        model.addAttribute("patients", patientRepository.findAll());
    }

    model.addAttribute("keyword", keyword);
    return "search-patient";
}
@GetMapping("/signup")
public String showSignupPage() {
    return "signup";
}
@PostMapping("/signup")
public String signup(
        @RequestParam String fullName,
        @RequestParam String email,
        @RequestParam String password) {

    if (userAccountRepository.existsByEmail(email)) {
        return "redirect:/signup";
    }

    UserAccount user = new UserAccount();
    user.setFullName(fullName);
    user.setEmail(email);

    String hashedPassword = passwordEncoder.encode(password);
    user.setPassword(hashedPassword);

    userAccountRepository.save(user);

    return "redirect:/login";
}
@GetMapping("/login")
public String showLoginPage() {
    return "login";
}

}
