package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import interfaz.ConstructorGrafico;
import interfaz.GraficoGUI;

public class AdminGrafico {
	
	private GraficoGUI interfaz;
	
	public AdminGrafico(GraficoGUI interfaz) {
		this.interfaz = interfaz;
	}
	
	public void graficarHora(String nombre, List<String> datosTabla) {
		if(datosTabla.size() != 0) {
			//Crea datos para graficar el promedio cada 5 minutos en la ultima hora
			Map<Date, List<Float>> mapeoPromedios= new LinkedHashMap<Date,List<Float>>();
			Calendar tiempo = Calendar.getInstance();
			
			Calendar tiempoM5 = Calendar.getInstance();
			tiempoM5.add(Calendar.MINUTE, -5);
			
			Calendar tiempoM10 = Calendar.getInstance();
			tiempoM10.add(Calendar.MINUTE, -10);
			
			Calendar tiempoM15 = Calendar.getInstance();
			tiempoM15.add(Calendar.MINUTE, -15);
			
			Calendar tiempoM20 = Calendar.getInstance();
			tiempoM20.add(Calendar.MINUTE, -20);
			
			Calendar tiempoM25 = Calendar.getInstance();
			tiempoM25.add(Calendar.MINUTE, -25);
			
			Calendar tiempoM30 = Calendar.getInstance();
			tiempoM30.add(Calendar.MINUTE, -30);
			
			Calendar tiempoM35 = Calendar.getInstance();
			tiempoM35.add(Calendar.MINUTE, -35);
			
			Calendar tiempoM40 = Calendar.getInstance();
			tiempoM40.add(Calendar.MINUTE, -40);
			
			Calendar tiempoM45 = Calendar.getInstance();
			tiempoM45.add(Calendar.MINUTE, -45);
			
			Calendar tiempoM50 = Calendar.getInstance();
			tiempoM50.add(Calendar.MINUTE, -50);
			
			Calendar tiempoM55 = Calendar.getInstance();
			tiempoM55.add(Calendar.MINUTE, -55);
			
			Calendar tiempoM60 = Calendar.getInstance();
			tiempoM60.add(Calendar.MINUTE, -60);
			
			mapeoPromedios.put(tiempo.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM5.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM10.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM15.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM20.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM25.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM30.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM35.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM40.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM45.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM50.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM55.getTime(), new ArrayList<Float>());
			
			Calendar fechaC = Calendar.getInstance();
			SimpleDateFormat formateador = AdminTabla.formateador;
			try {
				for(String dato: datosTabla) {
					String[] tokens = dato.split(",");
					Date fecha = formateador.parse(tokens[0]);
					fechaC.setTime(fecha);	
					Float porcentaje = (Float.parseFloat(tokens[1]))/100;
					//Comparo con -60 minutos para quedarme con los datos que estan dentro de la ultima hora
					//Despues con el resto en intervalos de 5 minutos
					if(tiempoM60.compareTo(fechaC) <= 0) {
						if(tiempoM55.compareTo(fechaC) >= 0) {
							mapeoPromedios.get(tiempoM55.getTime()).add(porcentaje);
						}
						else {
							if(tiempoM50.compareTo(fechaC) >= 0) {
								mapeoPromedios.get(tiempoM50.getTime()).add(porcentaje);
							}
							else {
								if(tiempoM45.compareTo(fechaC) >= 0) {
									mapeoPromedios.get(tiempoM45.getTime()).add(porcentaje);
								}
								else {
									if(tiempoM40.compareTo(fechaC) >= 0) {
										mapeoPromedios.get(tiempoM40.getTime()).add(porcentaje);
									}
										else {
											if(tiempoM35.compareTo(fechaC) >= 0) {
												mapeoPromedios.get(tiempoM35.getTime()).add(porcentaje);
											}
											else {
												if(tiempoM30.compareTo(fechaC) >= 0) {
													mapeoPromedios.get(tiempoM30.getTime()).add(porcentaje);
												}
												else {
													if(tiempoM25.compareTo(fechaC) >= 0) {
														mapeoPromedios.get(tiempoM25.getTime()).add(porcentaje);
													}
													else {
														if(tiempoM20.compareTo(fechaC) >= 0) {
															mapeoPromedios.get(tiempoM20.getTime()).add(porcentaje);
														}
														else {
															if(tiempoM15.compareTo(fechaC) >= 0) {
																mapeoPromedios.get(tiempoM15.getTime()).add(porcentaje);
															}
															else {
																if(tiempoM10.compareTo(fechaC) >= 0) {
																	mapeoPromedios.get(tiempoM10.getTime()).add(porcentaje);
																}
																else {
																	if(tiempoM5.compareTo(fechaC) >= 0) {
																		mapeoPromedios.get(tiempoM5.getTime()).add(porcentaje);
																	}
																	else {
																		mapeoPromedios.get(tiempo.getTime()).add(porcentaje);
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
				}	
				
				ArrayList<Date> fechasGrafico = new ArrayList<Date>();
				ArrayList<Float> promedioGrafico = new ArrayList<Float>();
				for(Entry<Date, List<Float>> entry: mapeoPromedios.entrySet()) {
					if(entry.getValue().size() != 0) {
						fechasGrafico.add(entry.getKey());
						float acumulador = new Float(0);
						for(Float i: entry.getValue()) {
							acumulador += i;
						}
						promedioGrafico.add(acumulador/entry.getValue().size());
					}
					
				}
				
				interfaz.actualizarGrafico(ConstructorGrafico.getChart(fechasGrafico, promedioGrafico, "Altura promedio ultima hora - "+ nombre));
				
				
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		
		
	}
	
	public void graficarDia(String nombre, List<String> datosTabla) {
		if(datosTabla.size() != 0) {
			//Crea datos para graficar el promedio cada hora del ultimo dia
			Map<Date, List<Float>> mapeoPromedios= new LinkedHashMap<Date,List<Float>>();
			Calendar tiempo = Calendar.getInstance();
			
			Calendar tiempoM1 = Calendar.getInstance();
			tiempoM1.add(Calendar.HOUR, -1);
			
			Calendar tiempoM2 = Calendar.getInstance();
			tiempoM2.add(Calendar.HOUR, -2);
			
			Calendar tiempoM3 = Calendar.getInstance();
			tiempoM3.add(Calendar.HOUR, -3);
			
			Calendar tiempoM4 = Calendar.getInstance();
			tiempoM4.add(Calendar.HOUR, -4);
			
			Calendar tiempoM5 = Calendar.getInstance();
			tiempoM5.add(Calendar.HOUR, -5);
			
			Calendar tiempoM6 = Calendar.getInstance();
			tiempoM6.add(Calendar.HOUR, -6);
			
			Calendar tiempoM7 = Calendar.getInstance();
			tiempoM7.add(Calendar.HOUR, -7);
			
			Calendar tiempoM8 = Calendar.getInstance();
			tiempoM8.add(Calendar.HOUR, -8);
			
			Calendar tiempoM9 = Calendar.getInstance();
			tiempoM9.add(Calendar.HOUR, -9);
			
			Calendar tiempoM10 = Calendar.getInstance();
			tiempoM10.add(Calendar.HOUR, -10);
			
			Calendar tiempoM11 = Calendar.getInstance();
			tiempoM11.add(Calendar.HOUR, -11);
			
			Calendar tiempoM12 = Calendar.getInstance();
			tiempoM12.add(Calendar.HOUR, -12);
			
			Calendar tiempoM13 = Calendar.getInstance();
			tiempoM13.add(Calendar.HOUR, -13);
			
			Calendar tiempoM14 = Calendar.getInstance();
			tiempoM14.add(Calendar.HOUR, -14);
			
			Calendar tiempoM15 = Calendar.getInstance();
			tiempoM15.add(Calendar.HOUR, -15);
			
			Calendar tiempoM16 = Calendar.getInstance();
			tiempoM16.add(Calendar.HOUR, -16);
			
			Calendar tiempoM17 = Calendar.getInstance();
			tiempoM17.add(Calendar.HOUR, -17);
			
			Calendar tiempoM18 = Calendar.getInstance();
			tiempoM18.add(Calendar.HOUR, -18);
			
			Calendar tiempoM19 = Calendar.getInstance();
			tiempoM19.add(Calendar.HOUR, -19);
			
			Calendar tiempoM20 = Calendar.getInstance();
			tiempoM20.add(Calendar.HOUR, -20);
			
			Calendar tiempoM21 = Calendar.getInstance();
			tiempoM21.add(Calendar.HOUR, -21);
			
			Calendar tiempoM22 = Calendar.getInstance();
			tiempoM22.add(Calendar.HOUR, -22);
			
			Calendar tiempoM23 = Calendar.getInstance();
			tiempoM23.add(Calendar.HOUR, -23);
			
			Calendar tiempoM24 = Calendar.getInstance();
			tiempoM24.add(Calendar.HOUR, -24);
			
			mapeoPromedios.put(tiempo.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM1.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM2.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM3.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM4.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM5.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM6.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM7.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM8.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM9.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM10.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM11.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM12.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM13.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM14.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM15.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM16.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM17.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM18.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM19.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM20.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM21.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM22.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM23.getTime(), new ArrayList<Float>());
			
			Calendar fechaC = Calendar.getInstance();
			SimpleDateFormat formateador = AdminTabla.formateador;
			try {
				for(String dato: datosTabla) {
					String[] tokens = dato.split(",");
					Date fecha = formateador.parse(tokens[0]);
					fechaC.setTime(fecha);
					Float porcentaje = (Float.parseFloat(tokens[1]))/100;
					//Comparo con -24 horas para quedarme con los datos que estan dentro de la ultima hora
					//Despues con el resto en intervalos de 1 hora
					if(tiempoM24.compareTo(fechaC) <= 0) {
						if(tiempoM23.compareTo(fechaC) >= 0) {
							mapeoPromedios.get(tiempoM23.getTime()).add(porcentaje);
						}
						else {
							if(tiempoM22.compareTo(fechaC) >= 0) {
								mapeoPromedios.get(tiempoM22.getTime()).add(porcentaje);
							}
							else {
								if(tiempoM21.compareTo(fechaC) >= 0) {
									mapeoPromedios.get(tiempoM21.getTime()).add(porcentaje);
								}
								else {
									if(tiempoM20.compareTo(fechaC) >= 0) {
										mapeoPromedios.get(tiempoM20.getTime()).add(porcentaje);
									}
									else {
										if(tiempoM19.compareTo(fechaC) >= 0) {
											mapeoPromedios.get(tiempoM19.getTime()).add(porcentaje);
										}
										else {
											if(tiempoM18.compareTo(fechaC) >= 0) {
												mapeoPromedios.get(tiempoM18.getTime()).add(porcentaje);
											}
											else {
												if(tiempoM17.compareTo(fechaC) >= 0) {
													mapeoPromedios.get(tiempoM17.getTime()).add(porcentaje);
												}
												else {
													if(tiempoM16.compareTo(fechaC) >= 0) {
														mapeoPromedios.get(tiempoM16.getTime()).add(porcentaje);
													}
													else {
														if(tiempoM15.compareTo(fechaC) >= 0) {
															mapeoPromedios.get(tiempoM15.getTime()).add(porcentaje);
														}
														else {
															if(tiempoM14.compareTo(fechaC) >= 0) {
																mapeoPromedios.get(tiempoM14.getTime()).add(porcentaje);
															}
															else {
																if(tiempoM13.compareTo(fechaC) >= 0) {
																	mapeoPromedios.get(tiempoM13.getTime()).add(porcentaje);
																}
																else {
																	if(tiempoM12.compareTo(fechaC) >= 0) {
																		mapeoPromedios.get(tiempoM12.getTime()).add(porcentaje);
																	}
																	else {
																		if(tiempoM11.compareTo(fechaC) >= 0) {
																			mapeoPromedios.get(tiempoM11.getTime()).add(porcentaje);
																		}
																		else {
																			if(tiempoM10.compareTo(fechaC) >= 0) {
																				mapeoPromedios.get(tiempoM10.getTime()).add(porcentaje);
																			}
																			else {
																				if(tiempoM9.compareTo(fechaC) >= 0) {
																					mapeoPromedios.get(tiempoM9.getTime()).add(porcentaje);
																				}
																				else {
																					if(tiempoM8.compareTo(fechaC) >= 0) {
																						mapeoPromedios.get(tiempoM8.getTime()).add(porcentaje);
																					}
																					else {
																						if(tiempoM7.compareTo(fechaC) >= 0) {
																							mapeoPromedios.get(tiempoM7.getTime()).add(porcentaje);
																						}
																						else {
																							if(tiempoM6.compareTo(fechaC) >= 0) {
																								mapeoPromedios.get(tiempoM6.getTime()).add(porcentaje);
																							}
																							else {
																								if(tiempoM5.compareTo(fechaC) >= 0) {
																									mapeoPromedios.get(tiempoM5.getTime()).add(porcentaje);
																								}
																								else {
																									if(tiempoM4.compareTo(fechaC) >= 0) {
																										mapeoPromedios.get(tiempoM4.getTime()).add(porcentaje);
																									}
																									else {
																										if(tiempoM3.compareTo(fechaC) >= 0) {
																											mapeoPromedios.get(tiempoM3.getTime()).add(porcentaje);
																										}
																										else {
																											if(tiempoM2.compareTo(fechaC) >= 0) {
																												mapeoPromedios.get(tiempoM2.getTime()).add(porcentaje);
																											}
																											else {
																												if(tiempoM1.compareTo(fechaC) >= 0) {
																													mapeoPromedios.get(tiempoM1.getTime()).add(porcentaje);
																												}
																												else {
																													mapeoPromedios.get(tiempo.getTime()).add(porcentaje);
																												}
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}	
				
				ArrayList<Date> fechasGrafico = new ArrayList<Date>();
				ArrayList<Float> promedioGrafico = new ArrayList<Float>();
				for(Entry<Date, List<Float>> entry: mapeoPromedios.entrySet()) {
					if(entry.getValue().size() != 0) {
						fechasGrafico.add(entry.getKey());
						float acumulador = 0;
						for(Float i: entry.getValue()) {
							acumulador += i;
						}
						promedioGrafico.add(acumulador/entry.getValue().size());
					}
					
				}
				
				interfaz.actualizarGrafico(ConstructorGrafico.getChart(fechasGrafico, promedioGrafico, "Altura promedio ulitmo dia - "+ nombre));
				
				
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		
		
		
	}
	
	public void graficarSemana(String nombre, List<String> datosTabla) {
		if(datosTabla.size() != 0) {
			//Crea datos para graficar el promedio cada dia de la ultima semana
			Map<Date, List<Float>> mapeoPromedios= new LinkedHashMap<Date,List<Float>>();
			Calendar tiempo = Calendar.getInstance();
			
			Calendar tiempoM1 = Calendar.getInstance();
			tiempoM1.add(Calendar.HOUR, -1);
			
			Calendar tiempoM2 = Calendar.getInstance();
			tiempoM2.add(Calendar.HOUR, -2);
			
			Calendar tiempoM3 = Calendar.getInstance();
			tiempoM3.add(Calendar.HOUR, -3);
			
			Calendar tiempoM4 = Calendar.getInstance();
			tiempoM4.add(Calendar.HOUR, -4);
			
			Calendar tiempoM5 = Calendar.getInstance();
			tiempoM5.add(Calendar.HOUR, -5);
			
			Calendar tiempoM6 = Calendar.getInstance();
			tiempoM6.add(Calendar.HOUR, -6);
			
			Calendar tiempoM7 = Calendar.getInstance();
			tiempoM7.add(Calendar.HOUR, -7);
		
			mapeoPromedios.put(tiempo.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM1.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM2.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM3.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM4.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM5.getTime(), new ArrayList<Float>());
			mapeoPromedios.put(tiempoM6.getTime(), new ArrayList<Float>());
			
			Calendar fechaC = Calendar.getInstance();
			SimpleDateFormat formateador = AdminTabla.formateador;
			try {
				for(String dato: datosTabla) {
					String[] tokens = dato.split(",");
					Date fecha = formateador.parse(tokens[0]);
					fechaC.setTime(fecha);
					Float porcentaje = (Float.parseFloat(tokens[1]))/100;
					//Comparo con -7 dias para quedarme con los datos que estan dentro de la ultima hora
					//Despues con el resto en intervalos de 1 dia
					if(tiempoM7.compareTo(fechaC) <= 0) {
						if(tiempoM6.compareTo(fechaC) >= 0) {
							mapeoPromedios.get(tiempoM6.getTime()).add(porcentaje);
						}
						else {
							if(tiempoM5.compareTo(fechaC) >= 0) {
								mapeoPromedios.get(tiempoM5.getTime()).add(porcentaje);
							}
							else {
								if(tiempoM4.compareTo(fechaC) >= 0) {
									mapeoPromedios.get(tiempoM4.getTime()).add(porcentaje);
								}
								else {
									if(tiempoM3.compareTo(fechaC) >= 0) {
										mapeoPromedios.get(tiempoM3.getTime()).add(porcentaje);
									}
									else {
										if(tiempoM2.compareTo(fechaC) >= 0) {
											mapeoPromedios.get(tiempoM2.getTime()).add(porcentaje);
										}
										else {
											if(tiempoM1.compareTo(fechaC) >= 0) {
												mapeoPromedios.get(tiempoM1.getTime()).add(porcentaje);
											}
											else {
												mapeoPromedios.get(tiempo.getTime()).add(porcentaje);
											}
										}
									}
								}
							}
						}
					}
				}	
				
				ArrayList<Date> fechasGrafico = new ArrayList<Date>();
				ArrayList<Float> promedioGrafico = new ArrayList<Float>();
				for(Entry<Date, List<Float>> entry: mapeoPromedios.entrySet()) {
					if(entry.getValue().size() != 0) {
						fechasGrafico.add(entry.getKey());
						float acumulador = 0;
						for(Float i: entry.getValue()) {
							acumulador += i;
						}
						promedioGrafico.add(acumulador/entry.getValue().size());
					}
					
				}
				
				interfaz.actualizarGrafico(ConstructorGrafico.getChart(fechasGrafico, promedioGrafico, "Altura promedio ultima semana - "+ nombre));
				
				
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			
		}
	}
		

}
