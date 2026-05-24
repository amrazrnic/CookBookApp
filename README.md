#  CookBook App

CookBook App je Android mobilna aplikacija za pregled i upravljanje receptima. Omogućava korisnicima pretragu recepata, čuvanje favorita i vođenje liste za kupovinu. Aplikacija podržava autentikaciju korisnika putem Firebase servisa.

---

## Korištene tehnologije

- Java (Android)
- Android Studio
- Firebase Authentication
- RecyclerView
- SharedPreferences
- Git & GitHub


## Autentikacija korisnika

Aplikacija omogućava:
- Registraciju novog korisnika (ime, email, lozinka)
- Prijavu postojećeg korisnika
- Promjenu lozinke
- Odjavu iz aplikacije
- Svaki korisnik ima vlastite podatke i favorite

---

## Ekrani i funkcionalnosti

**Splash Screen**
- Animirani uvodni ekran pri pokretanju aplikacije

**Home**
- Prikaz svih dostupnih recepata
- Pretraga recepata u realnom vremenu
- Filtriranje po kategorijama (Pasta, Supa, Doručak, Salata, Desert, Pica...)

**Detail ekran recepta**
- Prikaz sastojaka i koraka pripreme
- Dodavanje recepta u favorite

**Favoriti**
- Prikaz svih omiljenih recepata korisnika

**Shopping lista**
- Lista osnovnih namirnica sa checkboxovima
- Ručni unos dodatnih namirnica
- Stanje se čuva nakon zatvaranja aplikacije

**Profil**
- Prikaz korisničkih podataka (ime, email)
- Promjena lozinke
- Pristup postavkama i odjava

**Postavke**
- Tamna tema, obavijesti, jezik

---

## Grafički interfejs

- Tamna tema
- Responzivan dizajn prilagođen mobilnim uređajima
- Fokus na jednostavnost i preglednost

---

## Sigurnost i privatnost

- Autentikacija putem Firebase Authentication servisa
- Svaki korisnik vidi samo svoje podatke i favorite
- Odjava briše aktivnu sesiju korisnika

