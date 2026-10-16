//IMPORT REACT UTILITIES
import React, { useState, useEffect, useContext } from "react";
import { ActivityIndicator, Modal, Pressable, KeyboardAvoidingView, StyleSheet, Text, TextInput, View, TouchableOpacity, StatusBar, Dimensions } from "react-native";
//colorpicker
import { ColorPicker } from 'react-native-color-picker'
import Slider from '@react-native-community/slider';
import tinycolor from "tinycolor2";
//icons
import { MaterialIcons } from '@expo/vector-icons';
import { FontAwesome } from '@expo/vector-icons';
//variable
import { useBottomTabBarHeight } from '@react-navigation/bottom-tabs';
import { useHeaderHeight } from '@react-navigation/elements';
//class
import TwokClass from '../../CLASS/TwokClass'
//context
import Context from '../../CONTEXT/ContextProvider'

//Pagina che permette di creare un nuovo twok vedendo le modifiche live
function NewTwokPage(props) {

    //globalVar
    const { globalVar } = useContext(Context)

    //const twok
    let setcolorSelected;
    const [text, setText] = useState('')
    const [newtext, setNewText] = useState('')
    const [bgcol, setBgcol] = useState('#FFFFFF');
    const [fontcol, setFontcol] = useState('#000000');
    const [fontsize, setFontsize] = useState(0);
    const [fonttype, setFonttype] = useState(0)
    const [halign, setAlign] = useState(0)
    const [valign, setVlign] = useState(0)
    const [lat, setLat] = useState(null)
    const [lon, setLon] = useState(null)
    //permission
    const [permission, setPermission] = useState(null)
    //show textInput
    const [display, setDisplay] = useState('none')
    //modal
    const [modalVisible, setModalVisible] = useState(false);
    const [modal, setModal] = useState()
    //loading
    const [loading, setLoading] = useState(false);
    //variable
    const [optionsHeight, setOptionsHeight] = useState(false);
    const tabBarHeight = useBottomTabBarHeight();
    const headerHeight = useHeaderHeight();

    //USEEFFECT
    //Mostra la possibilità di inviare il twok
    //Negata quando l'utente sta scrivendo il testo
    //Negata quando il dispositivo sta rilevando la posizione
    useEffect(() => {
        if (display == 'none') {
            props.navigation.setOptions({
                headerRight: () => (
                    <TouchableOpacity
                        disabled={loading == true ? true : false}
                        onPress={() => {
                            var twok = {
                                sid: globalVar.sid,
                                text: text,
                                bgcol: bgcol.replace('#', ''),
                                fontcol: fontcol.replace('#', ''),
                                fontsize: (fontsize >= 20 && fontsize < 30) ? 0 : (fontsize >= 30 && fontsize < 40) ? 1 : 2,
                                fonttype: fonttype,
                                halign: halign,
                                valign: valign,
                                lat: lat,
                                lon: lon,
                            }
                            inviaTwok(twok)
                        }}
                    >
                        <FontAwesome name="send-o" size={24} color={loading == true ? 'gray' : 'black'} />
                    </TouchableOpacity>
                ),
            });
        } else {
            props.navigation.setOptions({
                headerRight: () => (""),
            });
        }
    }, [props.navigation, text, bgcol, fontcol, fontsize, fonttype, halign, valign, lat, lon, loading, display]);

    //Verifica se l'utente ha abilitato i permessi per la rilevazione della posizione
    useEffect(() => { getPermissionDeclared() }, []);

    //FUNZIONI
    //Restituisce se l'utente ha abilitato i permessi per la rilevazione della posizione
    function getPermissionDeclared() {
        TwokClass.getPermissionDeclared()
            .then(result => { setPermission(result) })
            .catch(error => alert("Si è verifica un errore durante la verifica dei permessi per la rilevazione della posizione"))
    }
    //Restituisce LAT e LON della posizione corrente
    function getPosition() {
        TwokClass.locationPermissionAsync()
            .then(result => { setLat(result.coords.latitude), setLon(result.coords.longitude), setLoading(false) })
            .catch(error => alert("Si è verifica un errore durante la rilevazione della posizione"))
    }

    //Invia il twok creato
    function inviaTwok(twok) {
        TwokClass.addTwok(twok)
            .then(result => { 
                setText(''), setBgcol('#FFFFFF'), setFontcol('#000000'),
                setFontsize(0), setFonttype(0), setAlign(0), setVlign(0),
                setLat(null), setLon(null) 
            })
            .catch(error => alert("Si è verifica un errore durante l'invio del twok"))
    }

    //Apre il modaal per il colorPicker a seconda che sia BGCOL o FONTCOL
    const openModal = (cosaCambiare, funzioneAssociata) => {
        setModal(
            <Modal
                animationType="slide"
                transparent={true}
                visible={modalVisible}
                backdropOpacity={0.70}
            >
                <View style={styles.centeredView}>
                    <View style={styles.modalView}>
                        <View style={{ flexDirection: 'row', width: '100%', justifyContent: 'space-between' }}>
                            <Pressable onPress={() => setModal()}>
                                <FontAwesome name="close" size={24} color="black" />
                            </Pressable>
                            <Pressable onPress={() => { funzioneAssociata(setcolorSelected), setModal() }}>
                                <FontAwesome name="check" size={24} color="black" />
                            </Pressable>
                        </View>
                        <ColorPicker
                            oldColor={cosaCambiare}
                            onColorChange={color => { setcolorSelected = tinycolor(color).toHexString() }}
                            onColorSelected={color => { setcolorSelected = color }}
                            style={styles.colorPicker}
                        />
                    </View>
                </View>
            </Modal>
        );
        return
    }

    return (
        //Mostro la schermata per modificare il testo del twok
        display == 'flex' ?
            <View style={{ display: display, padding: 20, height: '100%' }} >
                <View style={{ justifyContent: 'space-between', flexDirection: 'row' }}>
                    <FontAwesome name="close" size={24} color="black" onPress={() => { setDisplay('none') }} />
                    <FontAwesome name="check" size={24} color="black" onPress={() => { setText(newtext), setDisplay('none') }} />
                </View>
                <KeyboardAvoidingView>
                    <View style={{ alignItems: 'center' }}>
                        <TextInput
                            enablesReturnKeyAutomatically
                            autoFocus
                            allowFontScaling={true}
                            maxLength={100}
                            multiline={true}
                            value={newtext}
                            placeholder="A cosa pensi?"
                            onChangeText={value => { setNewText(value) }}
                            style={{ marginTop: 30 }}
                        />
                    </View></KeyboardAvoidingView>
            </View>
        :
        //Mostro la schermata per modificare gli attributi del twok
            <View>
                <StatusBar barStyle='dark-content'></StatusBar>
                <View style={styles.container}>
                    <View style={styles.row} onLayout={(event) => { setOptionsHeight(event.nativeEvent.layout.height) }}>

                        {/*FontFamily*/}
                        <TouchableOpacity onPress={() => { (fonttype < 2) ? setFonttype((fonttype + 1)) : setFonttype(0) }} >

                            {(fonttype == 0) ? <Text style={{ fontFamily: "System", fontSize: 24, color: 'black' }}>Aa</Text>
                            : (fonttype == 1) ? <Text style={{ fontFamily: "monospace", fontSize: 24, color: 'black' }}>Aa</Text>
                            : <Text style={{ fontFamily: "serif", fontSize: 24, color: 'black' }}>Aa</Text>
                            }
                        </TouchableOpacity>

                        {/*Halign*/}
                        <TouchableOpacity onPress={() => { ((halign < 2) ? setAlign((halign + 1)) : setAlign(0)) }} >

                            {(halign == 0) ? <FontAwesome name="align-left" size={24} color="black" />
                            : (halign == 1) ? <FontAwesome name="align-center" size={24} color="black" />
                            : <FontAwesome name="align-right" size={24} color="black" />
                            }
                        </TouchableOpacity>

                        {/*Valign*/}
                        <TouchableOpacity onPress={() => { ((valign < 2) ? setVlign((valign + 1)) : setVlign(0)) }} >
                            
                            {(valign == 0) ? <MaterialIcons name="vertical-align-top" size={30} color="black" />
                            : (valign == 1) ? <MaterialIcons name="vertical-align-center" size={30} color="black" />
                            : <MaterialIcons name="vertical-align-bottom" size={30} color="black" />
                            }
                        </TouchableOpacity>


                        {/*Bgcol e Fontcol*/}
                        <Pressable onPress={() => { setModalVisible(!modalVisible), openModal(bgcol, setBgcol) }} title={"Sfondo"}><MaterialIcons name="format-color-fill" size={24} color={bgcol} /></Pressable>
                        <Pressable onPress={() => { setModalVisible(!modalVisible), openModal(fontcol, setFontcol) }} title={"Testo"}><MaterialIcons name="format-color-text" size={24} color={fontcol} /></Pressable>

                        {/*Posizione*/}
                        {lat ?
                            <TouchableOpacity style={styles.position} onPress={() => { setLat(null), setLon(null) }}>
                                <MaterialIcons name="location-on" size={30} color="black" />
                            </TouchableOpacity>
                        :
                            <TouchableOpacity style={styles.position} 
                                onPress={() => { 
                                    if (permission) { setLoading(true), getPosition() 
                                    } else { 
                                        alert("Non hai abilitato i permessi per la rilevazione della posizione") 
                                    }; 
                                }}
                            >
                                {loading == true ?
                                    <View style={styles.indicatorWrapper}>
                                        <ActivityIndicator size="small" />
                                    </View>
                                : <MaterialIcons name="location-off" size={30} color="black" />
                                }
                            </TouchableOpacity>
                        }
                    </View>

                    {/*Mostro il modal*/}
                    <View>{modal}</View>

                    {/*FontSize*/}
                    <View style={styles.slider}>
                        <Slider
                            minimumValue={20}
                            maximumValue={50}
                            color="secondary"
                            value={fontsize}
                            onValueChange={value => { setFontsize(value) }}
                        />
                    </View>

                    {/*Testo del twok*/}
                    <Pressable onPress={() => { setDisplay('flex'), setNewText(text) }} >
                        <View style={[styles.twokStyle(bgcol, valign, optionsHeight, tabBarHeight, headerHeight)]}>
                            <Text style={[styles.inputIOS(fontsize, halign, fonttype, fontcol)]} >{text}</Text>
                        </View>
                    </Pressable>

                </View>
            </View>

    );
};

const styles = StyleSheet.create({  //props.navigation.goBack()
    container: {
        paddingTop: 20,
        justifyContent: 'center',
    },
    twokStyle: (bgcol, valign, optionsHeight, tabBarHeight, headerHeight) => ({
        width: "100%",
        height: Dimensions.get('window').height - optionsHeight - tabBarHeight - headerHeight - 55,
        backgroundColor: bgcol,
        alignItems: 'stretch',
        justifyContent: (valign == 0) ? "flex-start" : (valign == 1) ? "center" : "flex-end",
    }),
    inputIOS: (fontsize, halign, fonttype, fontcol) => ({
        fontSize: fontsize,
        padding: 20,
        backgroundColor: 'transparent',
        textAlign: (halign == 0) ? "left" : (halign == 1) ? "center" : "right",
        fontFamily: (fonttype == 0) ? "System" : (fonttype == 1) ? "monospace" : "serif",
        color: fontcol
    }),
    row: {
        flexDirection: 'row',
        justifyContent: 'space-around',
    },
    slider: {
        paddingHorizontal: 20,
        paddingVertical: 10
    },
    column: {
        flexDirection: 'column',
    },
    centeredView: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        marginTop: '4%',
    },
    modalView: {
        backgroundColor: "white",
        borderRadius: 20,
        padding: 20,
        width: '80%',
        height: '80%',
        alignItems: "center",
        shadowColor: "#000",
        shadowOffset: {
            width: 0,
            height: 2
        },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 5
    },
    colorPicker: {
        flex: 1,
        width: '100%',
    },
    indicatorWrapper: {
        padding: 5,
    },
});

export default NewTwokPage;