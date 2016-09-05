//
//  Shop.swift
//  BeakonIOS
//
//  Created by Christopher on 8/31/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Shop: NSObject {
    var country = ""
    var id = ""
    var city = ""
    var timeZone = ""
    var merchantName = ""
    var adress = ""
    var image = ""
    var bussinesType = ""
    var latitude = "10.007043"
    var longitude = "-84.211205"
    var enable = true
    var pointsToGive = 800
    var totalGiftPoints = GiftPointsType()
    var departments = Array<Department>()
    var contactNumbers = Array<String>()
    var users = Array<User>()
    
    var totalGiftPointsPropeties: (GiftPointsType) {
        get {
            return (totalGiftPoints)
        }
        
        set(newVal) {
            totalGiftPoints = newVal
        }
    }
    
    var departmentsPropeties: (Array<Department>) {
        get {
            return (departments)
        }
        
        set(newVal) {
            departments = newVal
        }
    }
    
    var contactNumbersPropeties: (Array<String>) {
        get {
            return (contactNumbers)
        }
        
        set(newVal) {
            contactNumbers = newVal
        }
    }
    
    var usersPropeties: (Array<User>) {
        get {
            return (users)
        }
        
        set(newVal) {
            users = newVal
        }
    }
    
    var latitudePropeties: (String) {
        get {
            return (latitude)
        }
        
        set(newVal) {
            latitude = newVal
        }
    }
    
    var longitudePropeties: (String) {
        get {
            return (longitude)
        }
        
        set(newVal) {
            longitude = newVal
        }
    }
    
    var enablePropeties: (Bool) {
        get {
            return (enable)
        }
        
        set(newVal) {
            enable = newVal
        }
    }
    
    var pointsToGivePropeties: (Int) {
        get {
            return (pointsToGive)
        }
        
        set(newVal) {
            pointsToGive = newVal
        }
    }
    
    var merchantNamePropeties: (String) {
        get {
            return (merchantName)
        }
        
        set(newVal) {
            merchantName = newVal
        }
    }
    
    var adressPropeties: (String) {
        get {
            return (adress)
        }
        
        set(newVal) {
            adress = newVal
        }
    }
    
    var imagePropeties: (String) {
        get {
            return (image)
        }
        
        set(newVal) {
            image = newVal
        }
    }
    
    var bussinessTypePropeties: (String) {
        get {
            return (bussinesType)
        }
        
        set(newVal) {
            bussinesType = newVal
        }
    }
    
    var countryPropeties: (String) {
        get {
            return (country)
        }
        
        set(newVal) {
            country = newVal
        }
    }
    
    var idPropeties: (String) {
        get {
            return (id)
        }
        
        set(newVal) {
            id = newVal
        }
    }
    
    var cityPropeties: (String) {
        get {
            return (city)
        }
        
        set(newVal) {
            city = newVal
        }
    }
    
    var timezonePropeties: (String) {
        get {
            return (timeZone)
        }
        
        set(newVal) {
            timeZone = newVal
        }
    }
    

}
