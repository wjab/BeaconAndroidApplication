//
//  Promo.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/30/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Promo: NSObject {
    var id = ""
    var type = ""
    var merchantId = ""
    var departmentId = ""
    var productId = ""
    var code = ""
    var enable = true
    var descriptionPromo = ""
    var giftPoints = 123
    var title = ""
    var images = ""
    
    var idPropeties: (String) {
        get {
            return (id)
        }
        
        set(newVal) {
            id = newVal
        }
    }
    
    var merchantIdPropeties: (String) {
        get {
            return (merchantId)
        }
        
        set(newVal) {
            merchantId = newVal
        }
    }
    
    var productIdPropeties: (String) {
        get {
            return (productId)
        }
        
        set(newVal) {
            productId = newVal
        }
    }
    
    var departmentIdPropeties: (String) {
        get {
            return (departmentId)
        }
        
        set(newVal) {
            departmentId = newVal
        }
    }
    
    var typePropeties: (String) {
        get {
            return (type)
        }
        
        set(newVal) {
            type = newVal
        }
    }
    
    var codePropeties: (String) {
        get {
            return (code)
        }
        
        set(newVal) {
            code = newVal
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
    
    var descriptionPromoPropeties: (String) {
        get {
            return (descriptionPromo)
        }
        
        set(newVal) {
            descriptionPromo = newVal
        }
    }
    
    var giftPointsPropeties: (Int) {
        get {
            return (giftPoints)
        }
        
        set(newVal) {
            giftPoints = newVal
        }
    }
    
    var titlePropeties: (String) {
        get {
            return (title)
        }
        
        set(newVal) {
           title = newVal
        }
    }
    
    var imagesPropeties: (String) {
        get {
            return (images)
        }
        
        set(newVal) {
            images = newVal
        }
    }
    
}
