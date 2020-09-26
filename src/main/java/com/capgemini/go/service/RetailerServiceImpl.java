package com.capgemini.go.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.go.dao.RetailerDao;
import com.capgemini.go.dto.RetailerDto;
import com.capgemini.go.excpetion.RetailerExcpetion;

@Service
public class RetailerServiceImpl implements RetailerService {
	
	@Autowired
	RetailerDao retailerDao;

	@Override
	public RetailerDto addRetailer(RetailerDto retailer) {
		if(!retailerDao.existsByEmail(retailer.getEmail())) {
		int tempNumber=retailerDao.getAllRetailerIds().size();
		String temp=retailerDao.getAllRetailerIds().get(tempNumber-1);
		tempNumber=Integer.parseInt(temp.substring(1))+1;
		temp="R"+tempNumber;
		retailer.setRetailerId(temp);
		return retailerDao.save(retailer);
		}
		else {
			throw new RetailerExcpetion("Retailer already exist with same email!");
		}
	}

	@Override
	public RetailerDto updateRetailer(RetailerDto retailer) {
		if(retailerDao.existsById(retailer.getRetailerId())) {
		return retailerDao.save(retailer);
		}
		else {
			throw new RetailerExcpetion("Update Failed, Fail to find the retailer!");
		}
	}

	@Override
	public List<RetailerDto> viewRetailers() {
		List<RetailerDto> retailerList=(List<RetailerDto>)retailerDao.findAll();
		if(!retailerList.isEmpty()) {
		return retailerList;
		}
		else {
			throw new RetailerExcpetion("No Retailer Exist!");
		}
	}

	@Override
	public boolean deleteReatiler(String retailerId) {
		if(retailerDao.existsById(retailerId)) {
		retailerDao.deleteById(retailerId);
		return true;
		}
		else{
			throw new RetailerExcpetion("Deletion Failed, Fail to find the retailer!");
		}
	}

	@Override
	public RetailerDto getRetailer(String retailerId) {
		Optional<RetailerDto> retailerOptional =retailerDao.findById(retailerId);
		if(retailerOptional.isPresent()) {
		return retailerOptional.get();
		}
		else {
			throw new RetailerExcpetion("Fail to find the retailer!");
		}
	}
}
