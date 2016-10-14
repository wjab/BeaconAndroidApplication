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

class KonkatViewController: UIViewController
{
    @IBOutlet weak var imageView: UIImageView!
    let headers = [
        "Api-Key": Constants.general.kontakt_api_key,
        "Content-Type": Constants.general.content_type_json,
        "Accept" : Constants.general.header_accept
    ]
    
    var devicesManager: KTKDevicesManager!
    var connection: KTKDeviceConnection?
    var backgroundScanTask: UIBackgroundTaskIdentifier = UIBackgroundTaskInvalid
    var intervalTimeSeg : double_t = 15.0
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        // Initiate Devices Manager
        devicesManager = KTKDevicesManager(delegate: self)
        
        // Start Discovery
        // Scaneo en segundos
        devicesManager.startDevicesDiscoveryWithInterval(intervalTimeSeg)
    }
    
    func reinstateBackgroundTask()
    {
        if backgroundScanTask == UIBackgroundTaskInvalid
        {
            registerBackgroundTask()
        }
    }
    
    func registerBackgroundTask()
    {
        backgroundScanTask = UIApplication.sharedApplication().beginBackgroundTaskWithExpirationHandler({
            [unowned self] in self.endBackgroundTask()
        })
    }
    
    func endBackgroundTask()
    {
        NSLog("BackgroundScanTask scan task ended.")
        UIApplication.sharedApplication().endBackgroundTask(backgroundScanTask)
        backgroundScanTask = UIBackgroundTaskInvalid
    }
    
    func getDataForBeacon(beacon: CLBeacon)
    {
        // Parameters
        let parameters = [
            "proximity": beacon.proximityUUID.UUIDString,
            "major": beacon.major,
            "minor": beacon.minor
        ]
        
        // Log
        NSLog("Getting Data for Beacon with parameters: \(parameters)")
        
        // Get Device
        KTKCloudClient.sharedInstance().getObjects(KTKDevice.self, parameters: parameters) { [weak self] response, error in
            if let device = response?.objects?.first as? KTKDevice
            {
                self?.getActionForDevice(proximity: beacon.proximity, device: device)
            }
        }
    }
    
    func getActionForDevice(proximity proximity:CLProximity, device: KTKDevice)
    {
        // Log
        NSLog("Getting Action for Beacon with unique ID: \(device.uniqueID)")
        
        KTKCloudClient.sharedInstance().getObjects(KTKAction.self, parameters: ["uniqueId": device.uniqueID]) { [weak self] response, error in
            
            if  let action = response?.objects?.first as? KTKAction where action.proximity == proximity,
                let content = action.content where content.type == .Image
            {
                // Download Image
                content.downloadContentDataWithCompletion { data, error in
                    if let data = data, let image = UIImage(data: data)
                    {
                        self?.imageView.image = image
                    }
                }
            }
        }
    }
}

extension KonkatViewController: KTKDevicesManagerDelegate
{
    
    func devicesManagerDidFailToStartDiscovery(manager: KTKDevicesManager, withError error: NSError?)
    {
        
    }
    
    func devicesManager(manager: KTKDevicesManager, didDiscoverDevices devices: [KTKNearbyDevice]?)
    {
        NSLog("Devices Manager found \(devices?.count) kontakt devices")
        
        //For para conocer las promos
        for (index, element) in devices!.enumerate() {
            let url : String = Constants.ws_services.device + element.uniqueID!
            
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
                        if((response)["status"] as! String != Constants.ws_response_code.not_found && (response)["status"] as! String != Constants.ws_response_code.unauthorized)
                        {
                            var device = JSON as! NSDictionary
                            device = response.objectForKey("device") as! NSDictionary
                            let rangesList =  device.mutableArrayValueForKey("ranges")
                                
                            for (_, element) in rangesList.enumerate()
                            {
                                let idPromo = element.objectForKey("promoID")as! String
                                
                                //Llama a buscar la promo
                                let promo = UtilsC()
                                promo.serviceById(idPromo)
                            }
                        }
                        else if((response)["status"] as! String == Constants.ws_response_code.not_found)
                        {
                            print(Constants.info_messages.beacon_not_belongs_to_promo)
                        }
                        else
                        {
                            print(Constants.error_messages.get_beacon_info)
                        }
                        case .Failure(let error):
                            print(NSString(format : Constants.error_messages.call_to_ws , error ) )
                    }
            }

        }
     

        /*if let device = devices?.filter({ $0.uniqueID == "<#unique id#>" }).first {
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
        }*/
    }
}
