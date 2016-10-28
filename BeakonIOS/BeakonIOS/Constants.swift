//
//  Constants.swift
//  BeakonIOS
//
//  Created by Eduardo Trejos on 10/14/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import Foundation


struct Constants
{
    struct ws_response_code
    {
        static let not_found = "404"
        static let unauthorized = "401"
        static let server_error = "500"
        static let ok = "200"
        static let created = "201"
    }
    struct ws_services
    {
        static let profile = ""
        static let device = "http://bdevicedevel.cfapps.io/device/UID/"
    }
    struct general
    {
        static let kontakt_api_key = "ZtLtzUwyFjUFGlwjSxHoKsDKmyqjXNLc"
        static let content_type_json = "application/json"
        static let header_accept = "application/vnd.com.kontakt+json;version=8"
    }
    struct info_messages
    {
        static let beacon_not_belongs_to_promo = "El Beacon no esta asociado a ninguna promocion"
    }
    struct error_messages
    {
        static let get_beacon_info = "Problema al obtner datos del beacon"
        static let call_to_ws = "Hubo un error realizando la peticion: %@"
        static let call_to_ws_toast = "Hubo un error realizando la petición"
        static let call_to_ws_min_points = "Hubo un error obteniendo los puntos minimos"
        static let invalid_exchange_code = "Codigo invalido"
        static let error_exchange_points = "Hubo un error solicitando canjear puntos"
        static let refreshing_points_error = "Hubo un error refrescando los puntos"
        static let error_getting_min_points = "Hubo un error obteniendo los puntos minimos"
        static let error_request_give_points = "Hubo un error solicitando regalar los puntos"
        static let error_not_enough_points_to_give = "No tiene los suficientes puntos para regalar esa cantidad"
    }
    struct colors
    {
        static func getWhite() -> UIColor
        {
            return UIColor(red:1.00, green:1.00, blue:1.00, alpha:1.0)
        }
        static func getBlack() -> UIColor
        {
            return UIColor(red:0.06, green:0.07, blue:0.09, alpha:1.0)
        }
        static func getDarkGreen() -> UIColor
        {
            return UIColor(red:0.63, green:0.85, blue:0.25, alpha:1.0)
        }
        static func getLightBlue() -> UIColor
        {
            return UIColor(red:0.33, green:0.50, blue:0.68, alpha:1.0)
        }
        static func getDarkBlue() -> UIColor
        {
            return UIColor(red:0.11, green:0.32, blue:0.55, alpha:1.0)
        }
        static func getActionBarColor() -> UIColor
        {
            return UIColor(red:0.19, green:0.64, blue:0.42, alpha:1.0)
        }
        static func getGrey() -> UIColor
        {
            return UIColor(red:0.27, green:0.27, blue:0.27, alpha:1.0)
        }
        static func getLightGrey() -> UIColor
        {
            return UIColor(red:0.66, green:0.66, blue:0.66, alpha:1.0)
        }
        static func getMediumGrey() -> UIColor
        {
            return UIColor(red:0.44, green:0.44, blue:0.44, alpha:1.0)
        }
    }

    struct facebook 
    {
        static let error_general = "Error comunicación con facebook"
    }

    struct messages
    {
        
        static func availablePointsMessage(points: String, minimum: String) -> String
		{
            
            let availablePointsMessage = NSString(format: "Usted tiene un total de \(points)  pts disponibles para redimir, esta es la cantidad minima de puntos:  \(minimum)")
            
            return availablePointsMessage as String
        }
        
        static func exchangedPointsMessage(points: String) -> String
        {
            
            let exchangedPointsMessage = NSString(format: "Puntos obtenidos \(points)")
            
            return exchangedPointsMessage as String
        }
        
        static let created_success_toast = "Creado con exito"
    }
}