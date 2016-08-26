//
//  User.swift
//  BeakonIOS
//
//  Created by Christopher on 8/24/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class User: NSObject {
    
    var name = "nombre"
    var lastname = "apellido"
    var username = "algo@gmail.com"
    var enable = true
    var phone = "8888888"
    var totalGiftPoints = 123
    var socialNetworkType = "local"
    var email = "algo@gmail.com"
    var pathImage = "htttp"
    var preference = []
    var productWishList = []
    var gender = "women"
    var socialNetworkId = "123143"
    var socialNetworkJson = "{}"
    
    var namePropeties: (String) {
        get {
            return (name)
        }
        
        set(newVal) {
            name = newVal
        }
    }
    
    var lastnamePropeties: (String) {
        get {
            return (lastname)
        }
        
        set(newVal) {
            lastname = newVal
        }
    }
    
    var usernameropeties: (String) {
        get {
            return (username)
        }
        
        set(newVal) {
            username = newVal
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
    
    var phonePropeties: (String) {
        get {
            return (phone)
        }
        
        set(newVal) {
            phone = newVal
        }
    }
    
    var totalGiftPointsPropeties: (Int) {
        get {
            return (totalGiftPoints)
        }
        
        set(newVal) {
            totalGiftPoints = newVal
        }
    }
    
    var pathImagePropeties: (String) {
        get {
            return (pathImage)
        }
        
        set(newVal) {
            pathImage = newVal
        }
    }
    
    var emailPropeties: (String) {
        get {
            return (email)
        }
        
        set(newVal) {
            email = newVal
        }
    }
    
    var socialNetworkTypePropeties: (String) {
        get {
            return (socialNetworkType)
        }
        
        set(newVal) {
            socialNetworkType = newVal
        }
    }
    
    var preferencePropeties: (Array<Preference>) {
        get {
            return (preference) as! (Array<Preference>)
        }
        
        set(newVal) {
            preference = newVal
        }
    }
    
    var productWishListPropeties: (Array<Wish>) {
        get {
            return (productWishList) as! (Array<Wish>)
        }
        
        set(newVal) {
            productWishList = newVal
        }
    }

    var genderPropeties: (String) {
        get {
            return (gender)
        }
        
        set(newVal) {
            gender = newVal
        }
    }
    
    var socialNetworkIdPropeties: (String) {
        get {
            return socialNetworkId
        }
        
        set(newVal) {
            socialNetworkId = newVal
        }
    }
    
    var socialNetworkJsonPropeties: (String) {
        get {
            return socialNetworkJson
        }
        
        set(newVal) {
            socialNetworkJson = newVal
        }
    }
}
