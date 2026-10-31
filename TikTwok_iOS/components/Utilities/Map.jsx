//IMPORT REACT UTILITIES
import React, { useState } from 'react';
import { StyleSheet, View, Dimensions } from 'react-native';
//map
import MapView, { Marker } from 'react-native-maps';


//Mostra sulla mappa, attraverso un marker, la posizione del twok associato a un luogo
function Map(props) {

    //const posizione
    const [mapRegion] = useState(props.position);

    return (
        <View style={styles.map}>
            <MapView
                style={{ alignSelf: 'stretch', height: '100%' }}
                region={
                    mapRegion
                }
            >
                <Marker
                    coordinate={mapRegion}
                    title='Marker'
                />
            </MapView>
        </View>
    );
}

const styles = StyleSheet.create({
    map: {
        width: Dimensions.get('window').width,
        height: "100%",
        flex: 1
    },
});

export default Map;