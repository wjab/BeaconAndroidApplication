//
//  KonkatViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 10/3/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import KontaktSDK
import Alamofire
import SwiftyJSON
import JLToast

class KonkatViewController: UIViewController {
    
    @IBOutlet weak var imageView: UIImageView!
    let headers = [
        "Api-Key": "ZtLtzUwyFjUFGlwjSxHoKsDKmyqjXNLc",
        "Content-Type":"application/json",
        "Accept" : "application/vnd.com.kontakt+json;version=8"]
    //https://api.kontakt.io/device?uniqueId=aruM
    //http://bdevicedevel.cfapps.io/device/UID/Jwm8
    
    var devicesManager: KTKDevicesManager!
    var connection: KTKDeviceConnection?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Initiate Devices Manager
        devicesManager = KTKDevicesManager(delegate: self)
        
        // Start Discovery
        //Scanea cada hora
        devicesManager.startDevicesDiscoveryWithInterval(3.0)
    }
    
    func getDataForBeacon(beacon: CLBeacon) {
        // Parameters
        let parameters = [
            "proximity": beacon.proximityUUID.UUIDString,
            "major": beacon.major,
            "minor": beacon.minor
        ]
        
        // Log
        print("Getting Data for Beacon with parameters: \(parameters)")
        
        // Get Device
        KTKCloudClient.sharedInstance().getObjects(KTKDevice.self, parameters: parameters) { [weak self] response, error in
            if let device = response?.objects?.first as? KTKDevice {
                self?.getActionForDevice(proximity: beacon.proximity, device: device)
            }
        }
    }
    
    func getActionForDevice(proximity proximity:CLProximity, device: KTKDevice) {
        
        // Log
        print("Getting Action for Beacon with unique ID: \(device.uniqueID)")
        
        KTKCloudClient.sharedInstance().getObjects(KTKAction.self, parameters: ["uniqueId": device.uniqueID]) { [weak self] response, error in
            
            if  let action = response?.objects?.first as? KTKAction where action.proximity == proximity,
                let content = action.content where content.type == .Image
            {
                // Download Image
                content.downloadContentDataWithCompletion { data, error in
                    if let data = data, let image = UIImage(data: data) {
                        self?.imageView.image = image
                    }
                }
            }
        }
    }
}

extension KonkatViewController: KTKDevicesManagerDelegate {
    
    func devicesManagerDidFailToStartDiscovery(manager: KTKDevicesManager, withError error: NSError?) {
        
    }
    
    func devicesManager(manager: KTKDevicesManager, didDiscoverDevices devices: [KTKNearbyDevice]?) {
        
        print("Devices Manager found \(devices?.count) kontakt devices")
        //For para conocer las promos
        for (index, element) in devices!.enumerate() {
            let url : String = "http://bdevicedevel.cfapps.io/device/UID/"+element.uniqueID!
            //Crea el request
            Alamofire.request(.GET, url, headers: self.headers,encoding: .JSON)
                .responseJSON
                {
                    response in switch response.result
                    {
                    //Si la respuesta es satisfactoria
                    case .Success(let JSON):
                        let response = JSON as! NSDictionary
                        //Si la respuesta no tiene status 404
                        if((response)["status"] as! String != "404" && (response)["status"] as! String != "401")
                        {
                            var device = JSON as! NSDictionary
                            device = response.objectForKey("device") as! NSDictionary
                            let rangesList =  device.mutableArrayValueForKey("ranges")
                            for (_, element) in rangesList.enumerate() {
                                //print(index, ":", element)
                                let idPromo = element.objectForKey("promoID")as! String
                                //Llama a buscar la promo
                                let promo = UtilsC()
                                promo.serviceById(idPromo)
                            }
                             //self.devicesManager.stopDevicesDiscovery()
                            //self.devicesManager.startDevicesDiscoveryWithInterval(7200.0)
                        }
                        else if((response)["status"] as! String == "401"){
                            print("El Beakon no esta asociado a ninguna promocion")
                        }
                        else
                        {
                            print("Problema al obtner datos del beacon")
                        }
                    case .Failure(let error):
                        print("Hubo un error realizando la peticion: \(error)")
                    }
            }

        }
     

        if let device = devices?.filter({ $0.uniqueID == "<#unique id#>" }).first {
            manager.stopDevicesDiscovery()
            
            let configuration = KTKDeviceConfiguration(uniqueID: "<#unique id#>")
            configuration.major = 1000
            configuration.minor = 2000
            
            connection = KTKDeviceConnection(nearbyDevice: device)
            if let connection = connection {
                // Write Configuration
                connection.writeConfiguration(configuration) { synchronized, configuration, error in
                    if let _ = error {
                        print("Error while configuring")
                    }
                    else {
                        print("Configuration applied")
                    }
                }
                
                // Update Firmware
                KTKFirmware.getFirmwaresForUniqueIDs(["<#unique id#>"]) { firmware, error in
                    if let firmware = firmware?.first {
                        connection.updateWithFirmware(firmware, progress: { print("Firmware update progress: \($0)") }) { success, error in
                            if let _ = error {
                                print("Error while configuring")
                            }
                            else {
                                print("Firmware update completed")
                            }
                        }
                    }
                }
            }
        }
    }
}
