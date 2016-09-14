//
//  Notification.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/1/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Notification: NSObject {
    var id = ""
    var userId = ""
    var message = ""
    var type = ""
    var read = false
    var creationDate:Float = 1470882059436
    var readDate:Float = 1470882059436
    
    
    var idPropeties: (String) {
        get {
            return (id)
        }
        
        set(newVal) {
            id = newVal
        }
    }
    
    var userIdPropeties: (String) {
        get {
            return (userId)
        }
        
        set(newVal) {
            userId = newVal
        }
    }
    
    var messagePropeties: (String) {
        get {
            return (message)
        }
        
        set(newVal) {
            message = newVal
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
    
    var readPropeties: (Bool) {
        get {
            return (read)
        }
        
        set(newVal) {
            read = newVal
        }
    }
    
    var creationDatePropeties: (Float) {
        get {
            return (creationDate)
        }
        
        set(newVal) {
            creationDate = newVal
        }
    }
    
    var readDatePropeties: (Float) {
        get {
            return (readDate)
        }
        
        set(newVal) {
            readDate = newVal
        }
    }
}
