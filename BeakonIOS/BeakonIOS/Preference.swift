//
//  Preference.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/26/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Preference: NSObject {
    var preference = "preferencia"
    var state = "activo"
    
    var preferencePropeties: (String) {
        get {
            return (preference)
        }
        
        set(newVal) {
            preference = newVal
        }
    }
    
    var statePropeties: (String) {
        get {
            return (state)
        }
        
        set(newVal) {
            state = newVal
        }
    }
    

}
