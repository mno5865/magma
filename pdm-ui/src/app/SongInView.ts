/// file/component: SongInView
/// description: A SongInView object based on our EER model
/// Gregory Ojiem - gro3228

export interface SongInView {
  songTitle: string,
  artistName: string
  albumTitle: string
  runtime: number
  listenCount: number
  genre: string
  songId: number
  albumId: number
}
