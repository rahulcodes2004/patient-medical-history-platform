
package com.example.patienthistory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HealthOverview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private String bloodGroup;
    private String allergies;
    private String leftEyePower;
    private String rightEyePower;
    private Double height;
    private Double weight;
    private String existingConditions;
    private String currentMedicines;

    public HealthOverview() {
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getLeftEyePower() {
        return leftEyePower;
    }

    public void setLeftEyePower(String leftEyePower) {
        this.leftEyePower = leftEyePower;
    }

    public String getRightEyePower() {
        return rightEyePower;
    }

    public void setRightEyePower(String rightEyePower) {
        this.rightEyePower = rightEyePower;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getExistingConditions() {
        return existingConditions;
    }

    public void setExistingConditions(String existingConditions) {
        this.existingConditions = existingConditions;
    }

    public String getCurrentMedicines() {
        return currentMedicines;
    }

    public void setCurrentMedicines(String currentMedicines) {
        this.currentMedicines = currentMedicines;
    }
}
