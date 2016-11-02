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
    var updateTimer : NSTimer?
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        // Initiate Devices Manager
        devicesManager = KTKDevicesManager(delegate: self)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(KonkatViewController.reinstateBackgroundTask), name: UIApplicationDidBecomeActiveNotification, object: nil)
        
        // Start Discovery
        // Scaneo en segundos
        //devicesManager.startDevicesDiscoveryWithInterval(intervalTimeSeg)
        
        updateTimer = NSTimer.scheduledTimerWithTimeInterval(intervalTimeSeg, target: self,
                                                             selector: #selector(KonkatViewController.scanManager), userInfo: nil, repeats: true)
       
    }
    
    deinit
    {
        NSNotificationCenter.defaultCenter().removeObserver(self)
    }
    
    func reinstateBackgroundTask()
    {
        if updateTimer != nil && backgroundScanTask == UIBackgroundTaskInvalid
        {
            registerBackgroundTask()
        }
    }
    
    func registerBackgroundTask()
    {
        backgroundScanTask = UIApplication.sharedApplication().beginBackgroundTaskWithExpirationHandler({
            [unowned self] in self.endBackgroundTask()
        })
        assert(backgroundScanTask != UIBackgroundTaskInvalid)
            }
    
    func endBackgroundTask()
    {
        NSLog("BackgroundScanTask scan task ended.")
        UIApplication.sharedApplication().endBackgroundTask(backgroundScanTask)
        backgroundScanTask = UIBackgroundTaskInvalid
        devicesManager.stopDevicesDiscovery()
        updateTimer?.invalidate()
        updateTimer = nil
    }
    
    func scanManager()
    {
        switch UIApplication.sharedApplication().applicationState
        {
           case .Inactive:
                NSLog("Inactive state. Scan task ended.")
                updateTimer?.invalidate()
                updateTimer = nil
                if backgroundScanTask != UIBackgroundTaskInvalid
                {
                    endBackgroundTask()
                }
                break
        case .Background:
                //NSLog("Scan is backgrounded.")
                registerBackgroundTask()
                devicesManager.startDevicesDiscoveryWithInterval(intervalTimeSeg)

                break
            default:
                //print("Scan default")
                registerBackgroundTask()
                devicesManager.startDevicesDiscoveryWithInterval(intervalTimeSeg)
                break
        }
        
    }
    
    func getDataForBeacon(beacon: CLBeacon)
    {
        //print("getDataForBeacon")
        // Parameters
        let parameters = [
            "proximity": beacon.proximityUUID.UUIDString,
            "major": beacon.major,
            "minor": beacon.minor
        ]
        
        // Log
        //NSLog("Getting Data for Beacon with parameters: \(parameters)")
        
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
        //NSLog("Getting Action for Beacon with unique ID: \(device.uniqueID)")
        
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
        print("DidFailToStartDiscovery")
    }
    
    func devicesManager(manager: KTKDevicesManager, didDiscoverDevices devices: [KTKNearbyDevice]?)
    {
        //NSLog("Devices Manager found \(devices!.count) kontakt devices")
        
        //For para conocer las promos
        for (_, element) in devices!.enumerate() {
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
                        else if((response)["status"] as! String == Constants.ws_response_code.unauthorized)
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
        
        
    }
}
