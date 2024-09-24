//
//  NavigationReduxMapper.swift
//  dreimultiplatform
//
//  Created by Laila Becker on 02.11.22.
//  Copyright © 2022 dreipol GmbH. All rights reserved.
//

import Foundation

private extension Collection where Index: Strideable, Index.Stride: SignedInteger {
    func slidingPairs() -> some RandomAccessCollection<(Element, Element)> {
        indices.dropLast()
            .map { i in
                (self[i], self[index(after: i)])
            }
    }
}

public struct NavigationReduxMapper {
    private static func getDestination<Current, Destination>(state: ApplicationState,
                                                             current: Current.Type,
                                                             destination: Destination.Type) -> Destination?
    where Current: Screen, Destination: Screen {
        state.navigationState.screens.slidingPairs().last { (from, to) in
            from is Current && to is Destination
        }?.1 as? Destination
    }

    public static func from<Current, Destination>(_ current: Current.Type, to destination: Destination.Type) -> ReduxMapper<Bool, Bool>
    where Current: Screen, Destination: Screen {
        ReduxMapper { state in
            getDestination(state: state, current: Current.self, destination: Destination.self) != nil
        } action: { dispatch, state, newValue in
            if !newValue && state.navigationState.screens.last is Destination {
                dispatch(NavigationAction.Back())
            }
        }
    }

    public static func destinationInfo<Current, Destination>(from current: Current.Type,
                                                      to destination: Destination.Type) -> ReduxStateGetter<Destination?, Destination?>
    where Current: Screen, Destination: Screen {
        ReduxStateGetter { state in
            getDestination(state: state, current: Current.self, destination: Destination.self)
        }
    }
}
