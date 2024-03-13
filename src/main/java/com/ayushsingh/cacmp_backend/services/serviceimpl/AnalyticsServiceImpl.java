package com.ayushsingh.cacmp_backend.services.serviceimpl;

import com.ayushsingh.cacmp_backend.models.constants.ComplaintPriority;
import com.ayushsingh.cacmp_backend.models.constants.ComplaintStatus;
import com.ayushsingh.cacmp_backend.models.entities.Complaint;
import com.ayushsingh.cacmp_backend.repository.entities.*;
import com.ayushsingh.cacmp_backend.services.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintFeedbackRepository complaintFeedbackRepository;
    private final ConsumerRepository consumerRepository;
    private final ComplaintLocationRepository complaintLocationRepository;
    private final ConsumerAddressRepository consumerAddressRepository;
    private final DepartmentRepository departmentRepository;


    @Override
    public Map<String, Object> getAnalytics() {
        HashMap<String,Object> analyticsMap=new HashMap<>();

        //- Get the number of complaints-open, reviewed and pending
        List<Map<String,Long>> complaintsCountByStatus=complaintRepository.countByStatus();
        analyticsMap.put("complaintsCountByStatus",complaintsCountByStatus);


        //- Get the number of complaints-LOW, MEDIUM, HIGH
        List<Map<String,Long>> complaintsCountByPriority=complaintRepository.countByPriority();
        analyticsMap.put("complaintsCountByPriority",complaintsCountByPriority);


        //- Get the number of complaints department wise
        List<Map<String,Long>> complaintsCountByDepartment=complaintRepository.countByDepartment();
        analyticsMap.put("complaintsCountByDepartment",complaintsCountByDepartment);


        //- Get the complaint count by category
        List<Map<String,Long>> complaintsCountByCategory=complaintRepository.countByCategory();
        analyticsMap.put("complaintsCountByCategory",complaintsCountByCategory);


        //- Get the count of complaints by department wise, status wise
        List<Map<String,Object>> complaintsCountByDepartmentAndStatus=complaintRepository.countByDepartmentAndStatus();
        processComplaintCountByDepartmentAndStatus(complaintsCountByDepartmentAndStatus,analyticsMap);


        //- Get the count of complaints by department wise, priority wise
        List<Map<String,Object>> complaintsCountByDepartmentAndPriority=complaintRepository.countByDepartmentAndPriority();
        processComplaintCountByDepartmentAndPriority(complaintsCountByDepartmentAndPriority,analyticsMap);


        //Get the number of departments
        Long departmentCount=departmentRepository.count();
        analyticsMap.put("departmentCount",departmentCount);

        //Get the category count
        Long categoryCount=departmentRepository.count();
        analyticsMap.put("categoryCount",categoryCount);

        //Get the consumer count
        Long consumerCount=consumerRepository.count();
        analyticsMap.put("consumerCount",consumerCount);

        //Get total complaints count
        Long totalComplaintsCount=complaintRepository.count();
        analyticsMap.put("totalComplaintsCount",totalComplaintsCount);

        //TODO: Get the number of complaints submitted over a specified time period
//        return complaintRepository.countByCreatedAtBetween(startDate, endDate);


        //TODO: Get the number of complaints resolved/solved over a specified time period
//        return complaintRepository.countByClosedAtBetween(startDate, endDate);

        //TODO: Get the average time taken to resolve complaints
           String averageResolutionTime=this.calculateAverageResolutionTime();
            analyticsMap.put("averageResolutionTime",averageResolutionTime);

        //Get the distribution (count) of consumers by region (wardNo and same for pinCode)
        List<Map<String,Long>> consumerCountByWardNo=consumerRepository.countConsumersByWardNo();
        analyticsMap.put("consumerCountByWardNo",consumerCountByWardNo);
        List<Map<Long,Long>> consumerCountByPinCode=consumerRepository.countConsumersByPinCode();
        analyticsMap.put("consumerCountByPinCode",consumerCountByPinCode);

        // Get the count of complaints by pincode/wardNo
        List<Map<Long,Long>> complaintCountByPincode=complaintRepository.countComplaintsByPincode();
        analyticsMap.put("complaintCountByPincode",complaintCountByPincode);
        List<Map<String,Long>> complaintCountByWardNo=complaintRepository.countComplaintsByWardNo();
        analyticsMap.put("complaintCountByWardNo",complaintCountByWardNo);

        return analyticsMap;
    }

    private void processComplaintCountByDepartmentAndPriority(List<Map<String, Object>> complaintsCountByDepartmentAndPriority, HashMap<String, Object> analyticsMap) {
        Map<String, List<Map<String,Object>>> departmentMap = new HashMap<>();
        for (Map<String, Object> complaint : complaintsCountByDepartmentAndPriority) {
            String department = (String) complaint.get("department");
            ComplaintPriority priority = (ComplaintPriority) complaint.get("priority");
            Long count = ((Long) complaint.get("count"));

            if (!departmentMap.containsKey(department)) {
                departmentMap.put(department, new ArrayList<>());
            }

            List<Map<String, Object>> priorityList = departmentMap.get(department);
            Map<String, Object> priorityMap = new HashMap<>();
            priorityMap.put("priority", priority);
            priorityMap.put("count", count);
            priorityList.add(priorityMap);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, Object>>> entry : departmentMap.entrySet()) {
            Map<String, Object> departmentData = new HashMap<>();
            departmentData.put("departmentName", entry.getKey());
            departmentData.put("value", entry.getValue());
            result.add(departmentData);
        }
        analyticsMap.put("complaintsCountByDepartmentAndPriority", result);
    }

    private void processComplaintCountByDepartmentAndStatus(List<Map<String, Object>> complaintsCountByDepartmentAndStatus, HashMap<String, Object> analyticsMap) {
        Map<String, List<Map<String,Object>>> departmentMap = new HashMap<>();

        for (Map<String, Object> complaint : complaintsCountByDepartmentAndStatus) {
            String department = (String) complaint.get("department");
            String status = ((ComplaintStatus) complaint.get("status")).getValue();
            Long count = ((Long) complaint.get("count"));

            if (!departmentMap.containsKey(department)) {
                departmentMap.put(department, new ArrayList<>());
            }

            List<Map<String,Object>> statusList = departmentMap.get(department);
            Map<String,Object> statusMap=new HashMap<>();
            statusMap.put("status",status);
            statusMap.put("count",count);
            statusList.add(statusMap);
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<Map<String,Object>>> entry : departmentMap.entrySet()) {
            Map<String, Object> departmentData = new HashMap<>();
            departmentData.put("departmentName", entry.getKey());
            departmentData.put("value", entry.getValue());
            result.add(departmentData);
        }

        analyticsMap.put("complaintsCountByDepartmentAndStatus", result);
    }

    private String calculateAverageResolutionTime() {
        List<Complaint> resolvedComplaints = complaintRepository.findAllResolvedComplaints();
        long totalResolutionTime = 0;
        for (Complaint complaint : resolvedComplaints) {
            totalResolutionTime += complaint.getClosedAt().getTime() - complaint.getCreatedAt().getTime();
        }
        if (resolvedComplaints.isEmpty()) {
            return "No resolved complaints";
        } else {
            long averageResolutionTimeInMillis = totalResolutionTime / resolvedComplaints.size();
            long days = TimeUnit.MILLISECONDS.toDays(averageResolutionTimeInMillis);
            long hours = TimeUnit.MILLISECONDS.toHours(averageResolutionTimeInMillis) % 24;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(averageResolutionTimeInMillis) % 60;

            StringBuilder result = new StringBuilder();
            if (days > 0) {
                result.append(days).append(days == 1 ? " day" : " days");
            }
            if (hours > 0) {
                result.append((!result.isEmpty() ? ", " : "")).append(hours).append(hours == 1 ? " hour" : " hours");
            }
            if (minutes > 0) {
                result.append((!result.isEmpty() ? ", " : "")).append(minutes).append(minutes == 1 ? " minute" : " minutes");
            }
            return result.toString();
        }
    }
}
