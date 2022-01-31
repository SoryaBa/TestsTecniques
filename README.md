

							ENERCOOP - Test technique


Cette application lit la valeur du parametre "id" dans tous les fichiers contenus dans un repertoire ou un fichier .zip
et rend les 5 valeurs du parametre "id" qui apparaissent de plus et nombre de fois chacune d'elles apparait dans les fichiers.


Context : Preparation de l'entretien technique.


Contraintes: 
			- Les URLs sont dans des fichiers qui sont seulement dans le repertoire donné (pas dans les sous-repertoires du repertoire)
			- chaque ligne du repertoire ne peut contenir qu'une seule URL.
			
Possibilités:
			Lecture à partir d'un fichier .zip (par default) avec la methode: findFromZipFile(nomZip)
			Lecture à partir d'un repertoire avec la methode: findFromDir(nomREp)
			
Utilisation : 
			- importer le projet dans Eclipse: dans les Arguments de la methode main renseigner le nom du repoertoire où se trouve les fichier à lire
			     ou a partir des test unitaire renseigner le nom du repertoire à lire.
			- Executer le fichier .jar en passant le nom du repertoire à lire.
										
												java -jar enercooptest.jar "NomDuFichierZip"	
			                           
			Exemple dans le cas ou le fichier .zip est dans le meme repertoire que .jar : java -jar enercooptest.jar "logs.zip" 
			                         
			                         
			                         

Résultat:  
		les 5 id les plus fréquentes:
		                              	id=413, fréquence =>105
										id=505, fréquence =>102
										id=473, fréquence =>97
										id=512, fréquence =>96
										id=991, fréquence =>95
