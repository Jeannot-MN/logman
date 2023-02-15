/*
import React from 'react';
import Box from '@material-ui/core/Box';
import {createStyles, makeStyles} from '@material-ui/core/styles';
import LinearProgress from '@material-ui/core/LinearProgress';
import PauseCircleFilledIcon from '@material-ui/icons/PauseCircleFilled';
import PlayCircleFilledIcon from '@material-ui/icons/PlayCircleFilled';
import IconButton from '@material-ui/core/IconButton';
import VolumeMuteIcon from '@material-ui/icons/VolumeMute';
import VolumeDownIcon from '@material-ui/icons/VolumeDown';
import VolumeOffIcon from '@material-ui/icons/VolumeOff';
import VolumeUpIcon from '@material-ui/icons/VolumeUp';
import Forward10Icon from '@material-ui/icons/Forward10';
import Replay10Icon from '@material-ui/icons/Replay10';
import useVideo from 'react-use/lib/useVideo';

const styles = makeStyles(() =>
  createStyles({
    vid: {
      width: '100%',
      height: '100%',
      maxHeight: '500px',
      maxWidth: '500px',
      backgroundColor: 'black',
    },
    controls: {
      position: 'absolute',
      top: 0,
      left: 0,
      background: 'black',
      width: '100%',
    },
    wIcon: {
      color: 'white',
    },
  })
);

interface Props {
  name: string;
  src: string;
  className?: string;
}
export function Video({src, name, className}: Props) {
  const classes = styles();
  const [video, state, controls] = useVideo(
    <video
      className={classes.vid}
      onClick={() => {
        !state.playing ? controls.play() : controls.pause();
      }}
    >
      <track kind="captions" label={name} />
      <source src={src} type={`video/mp4`} />
    </video>
  );
  return (
    <Box width="100%" height="100%" position="relative" className={className}>
      {video}
      <Box
        className={classes.controls}
        onClick={(e) => {
          e.preventDefault();
          e.stopPropagation();
        }}
      >
        <Box display="flex">
          {!state.playing ? (
            <IconButton
              onClick={() => {
                controls.play();
              }}
            >
              <PlayCircleFilledIcon className={classes.wIcon} />
            </IconButton>
          ) : (
            <IconButton
              onClick={() => {
                controls.pause();
              }}
            >
              <PauseCircleFilledIcon className={classes.wIcon} />
            </IconButton>
          )}
          {state.muted ? (
            <IconButton
              onClick={() => {
                controls.unmute();
              }}
            >
              <VolumeOffIcon className={classes.wIcon} />
            </IconButton>
          ) : (
            <IconButton
              onClick={() => {
                controls.mute();
              }}
            >
              <VolumeMuteIcon className={classes.wIcon} />
            </IconButton>
          )}

          <IconButton
            onClick={() => {
              controls.volume(state.volume - 0.1);
            }}
          >
            <VolumeDownIcon className={classes.wIcon} />
          </IconButton>

          <IconButton
            onClick={() => {
              controls.volume(state.volume + 0.1);
            }}
          >
            <VolumeUpIcon className={classes.wIcon} />
          </IconButton>
          <IconButton
            onClick={() => {
              controls.seek(state.time - 10);
            }}
          >
            <Replay10Icon className={classes.wIcon} />
          </IconButton>
          <IconButton
            onClick={() => {
              controls.seek(state.time + 10);
            }}
          >
            <Forward10Icon className={classes.wIcon} />
          </IconButton>
        </Box>

        <LinearProgress
          color="primary"
          variant="buffer"
          value={(state.time / state.duration) * 100}
          valueBuffer={
            state.buffered && state.buffered.length > 0
              ? (state.buffered[0].end / state.duration) * 100
              : 0
          }
        />
      </Box>
    </Box>
  );
}
*/
export const age = 4;